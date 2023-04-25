package com.sunmnet.mediaroom.tabsp.record.impl.hik.services;


import com.sun.jna.NativeLong;
import com.sun.jna.Structure;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.tabsp.record.impl.hik.HcnetSDKError;
import com.sunmnet.mediaroom.tabsp.record.impl.hik.impl.HCNetSDKByJNA;

public abstract class AbstractSDK {
    protected String username,password,ip,port;
    public AbstractSDK(String username, String password, String ip, String port) {
        this.username = username;
        this.password = password;
        this.ip = ip;
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
    /**
     * 初始化
     * */
    public abstract void init();
    /**
     * 检查有效性
     * */
    public abstract boolean effection();
    protected NativeLong lUserID;
    protected HCNetSDKByJNA hcNetSDK;
    protected void load(){
        loadLib("hpr");
        loadLib("gnustl_shared");
        loadLib("opensslwrap");
        loadLib("HCCore");
        loadLib("HCCoreDevCfg");
        loadLib("HCPreview");
        loadLib("HCPlayBack");
        loadLib("HCGeneralCfgMgr");
        loadLib("HCVoiceTalk");
        loadLib("HCIndustry");
        loadLib("HCDisplay");
        loadLib("HCAlarm");
        loadLib("SystemTransform");
        loadLib("PlayCtrl");
        loadLib("PlayCtrl_L");
        loadLib("hcnetsdk");
    }
    private void loadLib(String libName)
    {
        try{
            System.loadLibrary(libName);
        }catch (UnsatisfiedLinkError e){
            e.printStackTrace();
        }
    }
    public String getLastError(){
        int code=hcNetSDK.NET_DVR_GetLastError();
        return HcnetSDKError.getError(code);
    }
    public boolean STD_CONTROLL(int dwCommand, Structure condBuffer, Structure outxmlBuffer, Structure statusBuffer) {
        HCNetSDKByJNA.NET_DVR_STD_CONTROL ability = new HCNetSDKByJNA.NET_DVR_STD_CONTROL();
        ability.lpCondBuffer = condBuffer == null ? null : condBuffer.getPointer();
        ability.dwCondSize = condBuffer == null ? 0 : condBuffer.size();
        ability.lpStatusBuffer = statusBuffer == null ? null : statusBuffer.getPointer();
        ability.dwStatusSize = statusBuffer == null ? 0 : statusBuffer.size();
        ability.lpXmlBuffer = outxmlBuffer == null ? null : outxmlBuffer.getPointer();
        ability.byDataType = 0;
        ability.dwXmlSize = outxmlBuffer == null ? 0 : outxmlBuffer.size();
        ability.write();
        return hcNetSDK.NET_DVR_STDControl(lUserID.intValue(), dwCommand, ability.getPointer());
    }

    public boolean GET_STDCONFIG(int cmd, Structure outBuffer) {
        return GET_STDCONFIG(cmd, outBuffer, null, null, null);
    }

    public boolean GET_STDCONFIG(int cmd, Structure outBuffer, Structure conditionBuffer) {
        return GET_STDCONFIG(cmd, outBuffer, conditionBuffer, null, null);
    }

    public boolean GET_STDCONFIG(int cmd, Structure outBuffer, Structure conditionBuffer, Structure statusBuffer) {
        return GET_STDCONFIG(cmd, outBuffer, conditionBuffer, statusBuffer, null);
    }


    public boolean SET_STDCONFIG(int cmd, Structure lpInbuffer) {
        return SET_STDCONFIG(cmd, lpInbuffer, null, null, null);
    }

    public boolean SET_STDCONFIG(int cmd, Structure lpInbuffer, Structure conditionBuffer) {
        return SET_STDCONFIG(cmd, lpInbuffer, conditionBuffer, null, null);
    }
    public boolean STD_CONTROL(int dwCommand,Structure condBuffer){
        return STD_CONTROL(dwCommand,condBuffer,null);
    }
    public boolean STD_CONTROL(int dwCommand,Structure condBuffer,Structure statusBuffer){
        return STD_CONTROL(dwCommand,condBuffer,statusBuffer,null);
    }
    public boolean STD_CONTROL(int dwCommand,Structure condBuffer,Structure statusBuffer,Structure outxmlBuffer)
    {
        HCNetSDKByJNA.NET_DVR_STD_CONTROL ability=new HCNetSDKByJNA.NET_DVR_STD_CONTROL();
        ability.lpCondBuffer = condBuffer==null?null:condBuffer.getPointer();
        ability.dwCondSize = condBuffer==null?0:condBuffer.size();
        ability.lpStatusBuffer = statusBuffer == null ? null : statusBuffer.getPointer();
        ability.dwStatusSize = statusBuffer == null ? 0 : statusBuffer.size();
        ability.lpXmlBuffer=outxmlBuffer==null?null:outxmlBuffer.getPointer();
        ability.byDataType=0;
        ability.dwXmlSize=outxmlBuffer==null?0:outxmlBuffer.size();
        ability.write();
        return hcNetSDK.NET_DVR_STDControl(this.lUserID.intValue(), dwCommand, ability.getPointer());
    }
    public boolean SET_STDCONFIG(int cmd, Structure lpInbuffer, Structure conditionBuffer, Structure statusBuffer) {
        return SET_STDCONFIG(cmd, lpInbuffer, conditionBuffer, statusBuffer, null);
    }

    public boolean SET_STDCONFIG(int cmd, Structure lpInbuffer, Structure conditionBuffer, Structure statusBuffer, Structure lpXmlBuffer) {
        HCNetSDKByJNA.NET_DVR_STD_CONFIG config = new HCNetSDKByJNA.NET_DVR_STD_CONFIG();
        config.lpCondBuffer = conditionBuffer == null ? null : conditionBuffer.getPointer();
        config.dwCondSize = conditionBuffer == null ? 0 : conditionBuffer.size();
        config.write();
        config.lpInBuffer = lpInbuffer==null?null:lpInbuffer.getPointer();
        config.dwInSize = lpInbuffer==null?0:lpInbuffer.size();
        config.write();
        config.lpOutBuffer = null;
        config.dwOutSize = 0;
        config.write();
        config.lpStatusBuffer = statusBuffer == null ? null : statusBuffer.getPointer();
        config.dwStatusSize = statusBuffer == null ? 0 : statusBuffer.size();
        config.write();
        if (lpXmlBuffer != null) {
            config.byDataType = 1;
            config.lpXmlBuffer = lpXmlBuffer.getPointer();
            config.dwXmlSize = lpXmlBuffer.size();
        } else {
            config.lpXmlBuffer = null;
            config.dwXmlSize = 0;
            config.byDataType = 0;
        }
        config.write();
        return hcNetSDK.NET_DVR_SetSTDConfig(lUserID.intValue(), cmd, config.getPointer());
    }

    public boolean GET_STDCONFIG(int cmd, Structure outBuffer, Structure conditionBuffer, Structure statusBuffer, Structure lpXmlBuffer) {
        HCNetSDKByJNA.NET_DVR_STD_CONFIG config = new HCNetSDKByJNA.NET_DVR_STD_CONFIG();
        config.lpCondBuffer = conditionBuffer == null ? null : conditionBuffer.getPointer();
        config.dwCondSize = conditionBuffer == null ? 0 : conditionBuffer.size();
        config.write();
        config.lpInBuffer = null;
        config.dwInSize = 0;
        config.write();
        config.lpOutBuffer = outBuffer == null ? null : outBuffer.getPointer();
        config.dwOutSize = outBuffer == null ? 0 : outBuffer.size();
        config.write();
        config.lpStatusBuffer = statusBuffer == null ? null : statusBuffer.getPointer();
        config.dwStatusSize = statusBuffer == null ? 0 : statusBuffer.size();
        config.write();
        if (lpXmlBuffer != null) {
            config.byDataType = 1;
            config.lpXmlBuffer = lpXmlBuffer.getPointer();
            config.dwXmlSize = lpXmlBuffer.size();
        } else {
            config.lpXmlBuffer = null;
            config.dwXmlSize = 0;
            config.byDataType = 0;
        }
        config.write();
        return hcNetSDK.NET_DVR_GetSTDConfig(this.lUserID.intValue(), cmd, config.getPointer());
    }
    public boolean getStdConfig(int cmd, Structure outBuffer, Structure conditionBuffer, Structure statusBuffer, Structure lpXmlBuffer){
        HCNetSDKByJNA.NET_DVR_STD_CONFIG config=new HCNetSDKByJNA.NET_DVR_STD_CONFIG();
        config.lpCondBuffer = conditionBuffer == null ? null : conditionBuffer.getPointer();
        config.dwCondSize = conditionBuffer == null ? 0 : conditionBuffer.size();
        config.write();
        config.lpInBuffer=null;
        config.dwInSize=0;
        config.write();
        config.lpOutBuffer = outBuffer == null ? null : outBuffer.getPointer();
        config.dwOutSize = outBuffer == null ? 0 : outBuffer.size();
        config.write();
        config.lpStatusBuffer = statusBuffer == null ? null : statusBuffer.getPointer();
        config.dwStatusSize = statusBuffer == null ? 0 : statusBuffer.size();
        config.write();
        if(lpXmlBuffer!=null){
            config.byDataType=1;
            config.lpXmlBuffer=lpXmlBuffer.getPointer();
            config.dwXmlSize=lpXmlBuffer.size();
        }else{
            config.lpXmlBuffer=null;
            config.dwXmlSize=0;
            config.byDataType=0;
        }
        config.write();
        return hcNetSDK.NET_DVR_GetSTDConfig(this.lUserID.intValue(),cmd,config.getPointer());
    }

    /**
     * 读取设备配置
     * @param cmd 命令指数
     * @param condBuffer 条件
     * @param id 登录ID
     * @param statusBuffer 命令执行状态返回对象
     * @param outxmlBuffer xml数据输出对象
     * */
    public boolean READ_STD_XML(int id,Structure condBuffer, Structure outxmlBuffer, Structure statusBuffer, int cmd){
        HCNetSDKByJNA.NET_DVR_STD_ABILITY ability=new HCNetSDKByJNA.NET_DVR_STD_ABILITY();
        ability.lpCondBuffer = condBuffer==null?null:condBuffer.getPointer();
        ability.dwCondSize = condBuffer==null?0:condBuffer.size();
        ability.lpStatusBuffer = statusBuffer == null ? null : statusBuffer.getPointer();
        ability.dwStatusSize = statusBuffer == null ? 0 : statusBuffer.size();
        ability.lpOutBuffer=outxmlBuffer==null?null:outxmlBuffer.getPointer();
        ability.dwOutSize=outxmlBuffer==null?0:outxmlBuffer.size();
        ability.write();
        return hcNetSDK.NET_DVR_GetSTDAbility(id,
                cmd,
                ability.getPointer());
    }
    /**
     * 读取设备配置
     *
     * @param cmd          命令指数
     * @param condBuffer   条件
     * @param id           登录ID
     * @param statusBuffer 命令执行状态返回对象
     * @param outxmlBuffer xml数据输出对象
     */
    public boolean GetSTDAbility(int id, Structure condBuffer, Structure outxmlBuffer, Structure statusBuffer, int cmd) {
        HCNetSDKByJNA.NET_DVR_STD_ABILITY ability = new HCNetSDKByJNA.NET_DVR_STD_ABILITY();
        ability.lpCondBuffer = condBuffer == null ? null : condBuffer.getPointer();
        ability.dwCondSize = condBuffer == null ? 0 : condBuffer.size();
        ability.lpStatusBuffer = statusBuffer == null ? null : statusBuffer.getPointer();
        ability.dwStatusSize = statusBuffer == null ? 0 : statusBuffer.size();
        ability.lpOutBuffer = outxmlBuffer == null ? null : outxmlBuffer.getPointer();
        ability.dwOutSize = outxmlBuffer == null ? 0 : outxmlBuffer.size();
        ability.write();
        return hcNetSDK.NET_DVR_GetSTDAbility(id, cmd, ability.getPointer());
    }
    public boolean GetSTDAbility(Structure outxmlBuffer, int cmd) {
        return this.GetSTDAbility(outxmlBuffer, null, cmd);
    }
    public boolean GetSTDAbility(Structure outxmlBuffer,Structure statusBuffer, int cmd) {
        return this.GetSTDAbility(null,outxmlBuffer, statusBuffer, cmd);
    }
    public boolean GetSTDAbility(Structure condBuffer, Structure outxmlBuffer, Structure statusBuffer, int cmd) {
        return this.GetSTDAbility(lUserID.intValue(), condBuffer, outxmlBuffer, statusBuffer, cmd);
    }
    /**
     * 海康的命令透传功能
     */
    public String Transmission(String str, String strInBuffer) {
        String temp="url="+str+"  ,参数："+strInBuffer;
        HCNetSDKByJNA.NET_DVR_XML_CONFIG_INPUT struInput = new HCNetSDKByJNA.NET_DVR_XML_CONFIG_INPUT();
        HCNetSDKByJNA.BYTE_ARRAY ptrUrl = new HCNetSDKByJNA.BYTE_ARRAY(HCNetSDKByJNA.BYTE_ARRAY_LEN);
        System.arraycopy(str.getBytes(), 0, ptrUrl.byValue, 0, str.length());
        ptrUrl.write();
        struInput.lpRequestUrl = ptrUrl.getPointer();
        struInput.dwRequestUrlLen = str.length();
        if (strInBuffer != null) {
            HCNetSDKByJNA.BYTE_ARRAY ptrByte = new HCNetSDKByJNA.BYTE_ARRAY(10 * HCNetSDKByJNA.BYTE_ARRAY_LEN);
            ptrByte.byValue = strInBuffer.getBytes();
            ptrByte.write();
            struInput.lpInBuffer = ptrByte.getPointer();
            struInput.dwInBufferSize = strInBuffer.length();
        } else {
            struInput.lpInBuffer = null;
            struInput.dwInBufferSize = 0;
        }
        struInput.dwSize = struInput.size();
        struInput.write();

        HCNetSDKByJNA.NET_DVR_XML_CONFIG_OUTPUT struOutput = new HCNetSDKByJNA.NET_DVR_XML_CONFIG_OUTPUT();
        HCNetSDKByJNA.BYTE_ARRAY ptrOutByte = new HCNetSDKByJNA.BYTE_ARRAY(HCNetSDKByJNA.ISAPI_DATA_LEN);
        struOutput.lpOutBuffer = ptrOutByte.getPointer();
        struOutput.dwOutBufferSize = HCNetSDKByJNA.ISAPI_DATA_LEN;
        HCNetSDKByJNA.BYTE_ARRAY ptrStatusByte = new HCNetSDKByJNA.BYTE_ARRAY(HCNetSDKByJNA.ISAPI_STATUS_LEN);
        struOutput.lpStatusBuffer = ptrStatusByte.getPointer();
        struOutput.dwStatusSize = HCNetSDKByJNA.ISAPI_STATUS_LEN;
        struOutput.dwSize = struOutput.size();
        struOutput.write();

        boolean isSuccess = Transmission(struInput, struOutput);
        String result = null;
        if (isSuccess) {
            ptrOutByte.read();
            temp+=" 执行成功!!!";
            RunningLog.warn(temp);
            if (ptrOutByte.byValue!=null&&ptrOutByte.byValue.length>0){
                if (ptrOutByte.byValue[0]==0){
                    result=" 执行成功!!!";
                }else result = new String(ptrOutByte.byValue);
            }else
                result = new String(ptrOutByte.byValue);
        } else {
            temp+="执行失败!!错误代码:" + hcNetSDK.NET_DVR_GetLastError();
            RunningLog.warn(temp);
        }
        return result;
    }

    public String PostTransmission(String url, String params) {
        url = "POST " + url;
        return Transmission(url, params);
    }
    public String PutTransmission(String url, String params){
        url="PUT "+url;
        return Transmission(url, params);
    }
    public String GetTransmission(String url, String params) {
        url = "GET " + url;
        return Transmission(url, params);
    }

    public boolean Transmission(HCNetSDKByJNA.NET_DVR_XML_CONFIG_INPUT lpInputParam, HCNetSDKByJNA.NET_DVR_XML_CONFIG_OUTPUT lpOutputParam) {
        if (lpInputParam != null)
            lpInputParam.write();
        if (lpOutputParam != null) lpOutputParam.write();
        return hcNetSDK.NET_DVR_STDXMLConfig(lUserID.intValue(), lpInputParam, lpOutputParam);
    }
    public boolean NET_DVR_STDXMLConfig(HCNetSDKByJNA.NET_DVR_XML_CONFIG_INPUT lpInputParam, HCNetSDKByJNA.NET_DVR_XML_CONFIG_OUTPUT lpOutputParam){
        return hcNetSDK.NET_DVR_STDXMLConfig(lUserID.intValue(), lpInputParam, lpOutputParam);
    }
}
