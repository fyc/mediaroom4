package com.sunmnet.mediaroom.brand.utils;

import com.hikvision.dmb.system.InfoSystemApi;
import com.sunmnet.mediaroom.common.tools.ShellUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 海康设备，使用dmb.jar操作接口
 */
public class HikDmbCommandExecutor implements ShellUtils.ICommandExecutor {

    /**
     * 必须要返回值，且不需要root权限的命令
     */
    private List<String> exCommands = new ArrayList<>();

    public HikDmbCommandExecutor() {
        exCommands.add("getprop");
        exCommands.add("netstat");
        exCommands.add("ps");
        exCommands.add("lsof");
        exCommands.add("which");
        exCommands.add("ls");
        exCommands.add("find");
        exCommands.add("df");
    }

    private boolean isExCommand(String command) {
        for (String ex : exCommands) {
            if (command.startsWith(ex))
                return true;
        }
        return false;
    }

    /**
     * 执行命令-多条
     *
     * @param commands
     * @param isRoot
     * @return
     */
    public ShellUtils.CommandResult execCommand(String[] commands, boolean isRoot) {
        if (!isRoot) {
            return new ShellUtils.DefaultCommandExecutor().execCommand(commands, false);
        }
        ShellUtils.CommandResult commandResult = new ShellUtils.CommandResult();
        int res = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder errorMsg = new StringBuilder();
        for (String command : commands) {
            if (command != null) {
                //0成功 -1失败
                if (isExCommand(command)) {
                    ShellUtils.CommandResult result = new ShellUtils.DefaultCommandExecutor().execCommand(commands, false);
                    successMsg.append(result.successMsg);
                    errorMsg.append(result.errorMsg);
                    res = result.result;
                } else {
                    res = InfoSystemApi.execCommand(command);
                }
                if (res == -1)
                    break;
            }
        }
        commandResult.result = res;
        commandResult.successMsg = successMsg.toString();
        commandResult.errorMsg = errorMsg.toString();
        return commandResult;
    }
}
