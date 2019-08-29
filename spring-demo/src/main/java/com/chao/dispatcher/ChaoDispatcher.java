package com.chao.dispatcher;

import com.chao.annotation.Autowired;
import com.chao.annotation.Controller;
import com.chao.annotation.Service;
import com.chao.annotation.UrlMapping;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

/**
 * @Filename: ChaoDispatcher
 * @Description:
 * @Version: 1.0
 * @Author: wangchao
 * @Email: wangchao@hellogeek.com
 * @date: 2019/7/29 ;
 */
@WebAppConfiguration

public class ChaoDispatcher extends HttpServlet {

    private Properties contextConfig = new Properties();

    private List<String> classPath = new ArrayList<>();

    private Map<String, Object> iocMap = new HashMap<>();

    private Map<String, Method> hadlerMapper = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        String contextPath = req.getContextPath();
        String s = requestURI.replaceAll(contextPath, "").replaceAll("/+", "/");

        if (!hadlerMapper.containsKey(s)){
            resp.getWriter().write("method not found 404");
        }

        Method method = hadlerMapper.get(s);

        String beanName = getBeanName(method.getDeclaringClass().getSimpleName());

        Map<String, String[]> parameterMap = req.getParameterMap();

        try {
            method.invoke(iocMap.get(beanName),new Object[]{req,resp,parameterMap});
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        //properties加载
        initProperties(config);
        //包扫描类
        initScenPackClass(contextConfig.getProperty("scanPackager"));
        //实例话类
        try {
            initClassInstence();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (Exception e) {

        }
        //初始化IOC容器
        initIocBox();

        //url映射method
        initUrlMethodMapping();

    }


    private void initUrlMethodMapping() {
        if (iocMap.isEmpty()) {
            return;
        }

        for (Map.Entry<String, Object> entry : iocMap.entrySet()) {
            Class<?> aClass = entry.getValue().getClass();
            if (!aClass.isAnnotationPresent(Controller.class)) {
                continue;
            }

            UrlMapping annotation = aClass.getAnnotation(UrlMapping.class);
            String baseUrl = annotation.value();

            for (Method method : aClass.getMethods()) {
                if (!method.isAnnotationPresent(UrlMapping.class)) {
                    continue;
                }

                UrlMapping annotation1 = method.getAnnotation(UrlMapping.class);
                String methodUrl = annotation1.value();
                String url = ("/" + baseUrl + "/" + methodUrl).replaceAll("/+", "/");
                System.out.println("mapping  " + url + "  " + method);
                hadlerMapper.put(url, method);
            }
        }
    }

    /**
     * create controller  service   obj
     *
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private void initClassInstence() throws Exception {

        if (classPath.isEmpty()) {
            System.out.println("calssPath is empty !!!");
            return;
        }

        for (String classp : classPath) {
            Class<?> aClass = Class.forName(classp);
            String beanName = getBeanName(aClass.getName());
            Object o = aClass.newInstance();
            //default
            if (aClass.isAnnotationPresent(Controller.class)) {
                Controller annotation = aClass.getAnnotation(Controller.class);
                if (!StringUtils.isEmpty(annotation.value())) {
                    beanName = annotation.value();
                }
                iocMap.put(beanName, o);
            } else if (aClass.isAnnotationPresent(Service.class)) {
                //自定义beanName 实例
                Service annotation = aClass.getAnnotation(Service.class);
                if (!StringUtils.isEmpty(annotation.value())) {
                    beanName = annotation.value();
                }
                iocMap.put(beanName, o);
                //接口实例
                for (Class<?> anInterface : aClass.getInterfaces()) {
                    if (iocMap.containsKey(anInterface.getName())) {
                        throw new Exception(" anInterface key is exes");
                    }
                    iocMap.put(anInterface.getName(), anInterface.newInstance());
                }

            } else {
                continue;
            }
        }


    }

    private String getBeanName(String name) {

        char[] chars = name.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

    private void initScenPackClass(String scanPackager) {
        URL resource = this.getClass().getClassLoader().getResource("/" + scanPackager.replaceAll("\\.", "/"));
        File file = new File(resource.getFile());
        for (File filePath : file.listFiles()) {
            if (filePath.isDirectory()) {
                //递归
                initScenPackClass(scanPackager + "." + filePath.getName());
            } else {
                if (!filePath.getName().endsWith(".class")) {
                    continue;
                }
                String calssName = (scanPackager + "." + filePath.getName().replaceAll(".class", ""));
                classPath.add(calssName);
            }
        }

    }

    private void initIocBox() {
        if (!iocMap.isEmpty()) {
            return;
        }
        //注解字段赋值
        for (Map.Entry<String, Object> obj : iocMap.entrySet()) {
            Field[] fields = obj.getValue().getClass().getDeclaredFields();
            for (Field field : fields) {
                if (!field.isAnnotationPresent(Autowired.class)) {
                    continue;
                }
                Autowired annotation = field.getAnnotation(Autowired.class);
                String beanName = annotation.value();
                if (StringUtils.isEmpty(beanName)) {
                    beanName = field.getType().getName();
                }

                //加注解 必须默认赋值
                field.setAccessible(true);

                try {
                    field.set(obj.getValue(), iocMap.get(beanName));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void initProperties(ServletConfig config) {
        String contextConfigLocation = config.getInitParameter("contextConfigLocation");
        InputStream asStream = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation);

        try {
            contextConfig.load(asStream);
            asStream.close();
        } catch (IOException e) {

        }
    }
}
