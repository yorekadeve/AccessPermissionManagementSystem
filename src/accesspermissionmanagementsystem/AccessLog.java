/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package accesspermissionmanagementsystem;

/**
 *
 * @author yorekadev
 */

public class AccessLog {

    private int logId;
    private int userId;
    private int sessionId;
    private int operationId;
    private int resourceId;
    private String targetRecord;
    private String actionTime;
    private String result;

    public AccessLog() {
    }

    public AccessLog(int logId, int userId, int sessionId, int operationId,
                     int resourceId, String targetRecord, String actionTime,
                     String result) {
        this.logId = logId;
        this.userId = userId;
        this.sessionId = sessionId;
        this.operationId = operationId;
        this.resourceId = resourceId;
        this.targetRecord = targetRecord;
        this.actionTime = actionTime;
        this.result = result;
    }

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getOperationId() {
        return operationId;
    }

    public void setOperationId(int operationId) {
        this.operationId = operationId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getTargetRecord() {
        return targetRecord;
    }

    public void setTargetRecord(String targetRecord) {
        this.targetRecord = targetRecord;
    }

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
