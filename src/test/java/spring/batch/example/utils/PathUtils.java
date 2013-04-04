package spring.batch.example.utils;

import java.net.URISyntaxException;

public class PathUtils {

    public static String getProjectPath() {
        String path = null;
        try {
            path = Thread.currentThread().getContextClassLoader().getResource("batch.properties").toURI().getPath();
            int index = path.indexOf("spring-batch-example");
            if (index != -1) {
                path = path.substring(0, index + 20);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return path;
    }

    public static void main(String[] args) {
        System.out.println(PathUtils.getProjectPath());
    }
}
