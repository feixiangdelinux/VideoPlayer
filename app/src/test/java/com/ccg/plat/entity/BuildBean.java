package com.ccg.plat.entity;

import com.blankj.utilcode.util.StringUtils;

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 20-4-22 下午4:13
 */
public class BuildBean {
    private final String middlePath = "src/main/java/";

    /**
     * Module文件位置
     */
    private String filePath;
    /**
     * 模块名称
     */
    private String applicationName;
    /**
     * 包名
     */
    private String packageName;
    /**
     * 默认Activity名称
     */
    private String activityName;
    /**
     * Activity布局文件名字
     */
    private String layoutId;


    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }


    public String getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(String layoutId) {
        this.layoutId = layoutId;
    }

    /**
     * 获取Module下包的具体路径
     *
     * @return
     */
    public String getPackageRoute() {
        String finalStr = packageName.replace(".", "/");
        return filePath + middlePath + finalStr + "/";
    }

    /**
     * 获取Module后缀(小写)
     *
     * @return
     */
    public String getSuffix() {
        int endPosition = applicationName.lastIndexOf(" ");
        return applicationName.substring(endPosition + 1);
    }

    /**
     * 获取ViewModel的名称
     *
     * @return
     */
    public String getViewModel() {
        return activityName.replace("Activity", "ViewModel");
    }

    /**
     * 获取ActivityBinding
     *
     * @return
     */
    public String getActivityBinding() {
        String tempStr = layoutId.replace("_", " ");
        String newStr = "";
        String[] arr = tempStr.split("\\s+");
        for (String ss : arr) {
            newStr = newStr + StringUtils.upperFirstLetter(ss);
        }
        return newStr + "Binding";
    }

}
