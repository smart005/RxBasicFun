package com.cloud.basicfun.beans;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @Author chenghailei
 * @Email:maplelucy1991@163.com
 * @CreateTime:17/7/4
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
@Entity(nameInDb = "breakPoint")
public class BreakPointBean {
    @Id(autoincrement = true)
    @Index
    @Property(nameInDb = "id")
    private Long id;
    @Index
    @Property(nameInDb = "originalFileName")
    private String originalFileName;
    @Property(nameInDb = "fileName")
    private String fileName;
    @Property(nameInDb = "filePath")
    private String filePath;
    @Property(nameInDb = "recordDirectory")
    private String recordDirectory;

    @Generated(hash = 123718171)
    public BreakPointBean(Long id, String originalFileName, String fileName,
            String filePath, String recordDirectory) {
        this.id = id;
        this.originalFileName = originalFileName;
        this.fileName = fileName;
        this.filePath = filePath;
        this.recordDirectory = recordDirectory;
    }

    @Generated(hash = 1522241638)
    public BreakPointBean() {
    }

    /**
     *
     */
    public String getRecordDirectory() {
        if (recordDirectory == null) {
            recordDirectory = "";
        }
        return recordDirectory;
    }

    /**
     * @param recordDirectory
     */
    public void setRecordDirectory(String recordDirectory) {
        this.recordDirectory = recordDirectory;
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
