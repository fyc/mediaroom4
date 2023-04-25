package com.sunmnet.mediaroom.common.tools;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by dengzl_pc on 2017/10/16.
 */
public class ShellUtils {

    public static final String TAG = "ShellUtils";

    public static String COMMAND_SU = "su";
    public final static String COMMAND_SH = "sh";
    public final static String COMMAND_EXIT = "exit\n";
    public final static String COMMAND_LINE_END = "\n";

    public static boolean checkRootPermission() {
        return execCommand("echo root", true).result == 0;
    }

    public static boolean hasRoot = false;

    public static void init() {
        hasRoot = checkRootPermission();
    }

    private static ICommandExecutor commandExecutor;

    public static ICommandExecutor getCommandExecutor() {
        if (commandExecutor == null) {
            commandExecutor = new DefaultCommandExecutor();
        }
        return commandExecutor;
    }

    public static void setCommandExecutor(ICommandExecutor commandExecutor) {
        ShellUtils.commandExecutor = commandExecutor;
    }

    public interface ICommandExecutor {
        ShellUtils.CommandResult execCommand(String[] commands, boolean isRoot);
    }

    public static boolean isDeviceRooted() {
        return /*checkRootMethod1() ||*/ checkRootMethod2() || checkRootMethod3();
    }

    private static boolean checkRootMethod1() {
        String buildTags = android.os.Build.TAGS;
        return buildTags != null && buildTags.contains("test-keys");
    }

    private static boolean checkRootMethod2() {
        String[] paths = {"/system/app/Superuser.apk", "/sbin/su", "/system/bin/su", "/system/xbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su",
                "/system/bin/failsafe/su", "/data/local/su", "/su/bin/su"};
        for (String path : paths) {
            if (new File(path).exists()) {
                COMMAND_SU = path;
                return true;
            }
        }
        return false;
    }

    private static boolean checkRootMethod3() {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(new String[]{"/system/xbin/which", "su"});
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            if (in.readLine() != null) return true;
            return false;
        } catch (Throwable t) {
            return false;
        } finally {
            if (process != null) process.destroy();
        }
    }

    /**
     * Command执行结果
     *
     * @author Mountain
     */
    public static class CommandResult {
        public int result = -1;
        public String errorMsg;
        public String successMsg;
    }

    /**
     * 执行命令—单条
     *
     * @param command
     * @param isRoot
     * @return
     */
    public static CommandResult execCommand(String command, boolean isRoot) {
        String[] commands = {command};
        return execCommand(commands, isRoot);
    }

    public static CommandResult execCommand(String command) {
        String[] commands = {command};
        return execCommand(commands, false);
    }

    public static CommandResult execCommand(String[] commands) {
        return execCommand(commands, false);
    }

    /**
     * 执行命令-多条
     *
     * @param commands
     * @param isRoot
     * @return
     */
    public static CommandResult execCommand(String[] commands, boolean isRoot) {
        return getCommandExecutor().execCommand(commands,isRoot);
    }

    public static class DefaultCommandExecutor implements ICommandExecutor {

        @Override
        public CommandResult execCommand(String[] commands, boolean isRoot) {
            CommandResult commandResult = new CommandResult();
            if (commands == null || commands.length == 0) return commandResult;
            Process process = null;
            DataOutputStream os = null;
            BufferedReader successResult = null;
            BufferedReader errorResult = null;
            StringBuilder successMsg = null;
            StringBuilder errorMsg = null;
            try {
                process = Runtime.getRuntime().exec(isRoot ? COMMAND_SU : COMMAND_SH);
                os = new DataOutputStream(process.getOutputStream());
                for (String command : commands) {
                    if (command != null) {
                        os.write(command.getBytes());
                        os.writeBytes(COMMAND_LINE_END);
                        os.flush();
                    }
                }
                os.writeBytes(COMMAND_EXIT);
                os.flush();
                commandResult.result = process.waitFor();
                //获取错误信息
                successMsg = new StringBuilder();
                errorMsg = new StringBuilder();
                successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
                errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String s;
                while ((s = successResult.readLine()) != null) {
                    System.out.println(s);
                    successMsg.append(s);
                }

                while ((s = errorResult.readLine()) != null) {
                    System.out.println(s);
                    errorMsg.append(s);
                }
                commandResult.successMsg = successMsg.toString();
                commandResult.errorMsg = errorMsg.toString();
            /*System.out.println(commandResult.result + " | " + commandResult.successMsg
                    + " | " + commandResult.errorMsg);*/
            } catch (IOException e) {
                String errmsg = e.getMessage();
                if (errmsg != null) {
                    System.out.println(errmsg);
                } else {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                String errmsg = e.getMessage();
                if (errmsg != null) {
                    System.out.println(errmsg);
                } else {
                    e.printStackTrace();
                }
            } finally {
                try {
                    if (os != null) os.close();
                    if (successResult != null) successResult.close();
                    if (errorResult != null) errorResult.close();
                } catch (IOException e) {
                    String errmsg = e.getMessage();
                    if (errmsg != null) {
                        System.out.println(errmsg);
                    } else {
                        e.printStackTrace();
                    }
                }
                if (process != null) process.destroy();
            }
            return commandResult;
        }
    }
}
