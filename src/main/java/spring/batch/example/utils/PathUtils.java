package spring.batch.example.utils;

import java.net.URISyntaxException;

public class PathUtils {

    private static final String PROJECT_NAME = "spring-batch-example";

    public static String getProjectPath() {
        String path = null;
        try {
            path = Thread.currentThread().getContextClassLoader().getResource("batch-oracle.properties").toURI().getPath();
            int index = path.indexOf(PROJECT_NAME);
            if (index != -1) {
                path = path.substring(0, index + PROJECT_NAME.length());
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
