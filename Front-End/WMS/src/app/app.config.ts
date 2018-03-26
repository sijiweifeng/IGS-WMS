export class AppConfig {
    //测试环境URL
    public static getBackEndUrl() {
        //return "http://192.168.27.80:8091";
        return "http://localhost:8090";
    }
    //获取设备高度
    public static getWindowHeight() {
        return window.screen.height;
    }
    //获取设备宽度
    public static getWindowWidth() {
        return window.screen.width;
    }
}