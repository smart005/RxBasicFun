package com.cloud.basicfun.beans;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @Author chenghailei
 * @Email:maplelucy1991@163.com
 * @CreateTime:17/7/3
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
@Entity(nameInDb = "appendPosition")
public class AppendPositionBean {
    @Id(autoincrement = true)
    @Index
    @Property(nameInDb = "id")
    private Long id;
    @Property(nameInDb = "position")
    private long position;
    @Index
    @Property(nameInDb = "originalFileName")
    private String originalFileName;
    @Property(nameInDb = "fileName")
    private String fileName;
    @Property(nameInDb = "filePath")
    private String filePath;

    @Generated(hash = 822151056)
    public AppendPositionBean(Long id, long position, String originalFileName,
            String fileName, String filePath) {
        this.id = id;
        this.position = position;
        this.originalFileName = originalFileName;
        this.fileName = fileName;
        this.filePath = filePath;
    }

    @Generated(hash = 1613621054)
    public AppendPositionBean() {
    }

    /**
     *
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     */
    public long getPosition() {
        return position;
    }

    /**
     * @param position
     */
    public void setPosition(long position) {
        this.position = position;
    }

    /**
     *
     */
    public String getOriginalFileName() {
        if (originalFileName == null) {
            originalFileName = "";
        }
        return originalFileName;
    }

    /**
     * @param originalFileName
     */
    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    /**
     *
     */
    public String getFileName() {
        if (fileName == null) {
            fileName = "";
        }
        return fileName;
    }

    /**
     * @param fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     *
     */
    public String getFilePath() {
        if (filePath == null) {
            filePath = "";
        }
        return filePath;
    }

    /**
     * @param filePath
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

}
