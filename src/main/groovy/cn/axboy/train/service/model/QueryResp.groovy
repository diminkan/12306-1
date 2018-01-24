package cn.axboy.train.service.model

/**
 * @author zcw
 * @date 2018/1/24 17:12
 * @version 1.0.0
 * 余票查询，12306接口返回值
 */
class QueryResp {

    List<String> messages

    String flag

    Map map

    List<String> result = null

    List<TicketInfo> format_result = []

    /**
     * 内部类，查询的车票信息
     */
    static class TicketInfo {
        //一字符串;作用不详
        String btn_title        //按钮标题;     预定，备注内容
        String train_no         //列车编号;     550000T10150
        String train_title      //车次;        T101
        String begin_station    //SNH;         起点站
///////////////5
        String end_station      //SZQ;         终点站
        String from_station     //GZG;         出发站
        String to_station       //SZQ;         到达站
        String go_time          //出发时间      02:00
        String arrive_time      //到达时间      08:15
///////////////10
        String cost_time        //耗时         06:15
//        Y,N
//        0UGL%2FVxUe58BTaDRmXgGnkbqUe6LbXZuxLWhuvyaxxyYJ8vSL%2BxDu9RXsGI%3D
        String begin_time       //起点站出发日期 20171006
//        3
///////////////15
//        Q6
//        01
//        02
//        0
//        0
///////////////20
//
//
//
        String left_bed_soft    //软卧余票
//
///////////////25
//
        String left_no_seat     //无座余票
//
        String left_bed_hard    //硬卧余票
        String left_seat_hard   //硬座余票
///////////////30
        String left_gao_2       //二等座余票
        String left_gao_1       //一等座余票
        String left_gao_0       //商务座余票
//
//        10401030，作用不详
///////////////35
//        1413，作用不详
    }

    /**
     * 座位类别
     */
    def seat_type = [
            A9: "商务座",
            M : "一等座",
            O : "二等座",
            //xx: "高级软卧",
            A4: "软卧",
            //xx: "动卧",
            A3: "硬卧",
            //xx: "软座",
            A1: "硬座",
            WZ: "无座",
            //xx: "其它",
            OT: []
    ]

    static QueryResp queryMapper(Map map) {
        QueryResp resp = new QueryResp()
        if (!map.data) {
            resp.messages = map.messages
            return resp
        }
        resp.flag = map.data.flag
        resp.map = map.data.map
        resp.result = map.data.result
        resp.result.each {
            def ticket = new TicketInfo()
            def arr = it.split("\\|")
            if (arr.length < 33) {
                return
            }
            ticket.btn_title = arr[1]
            ticket.train_no = arr[2]
            ticket.train_title = arr[3]
            ticket.begin_station = arr[4]
            ticket.end_station = arr[5]
            ticket.from_station = arr[6]
            ticket.to_station = arr[7]
            ticket.go_time = arr[8]
            ticket.arrive_time = arr[9]
            ticket.cost_time = arr[10]
            ticket.begin_time = arr[13]
            ticket.left_bed_soft = arr[23]
            ticket.left_no_seat = arr[26]
            ticket.left_bed_hard = arr[28]
            ticket.left_seat_hard = arr[29]
            ticket.left_gao_2 = arr[30]
            ticket.left_gao_1 = arr[31]
            ticket.left_gao_0 = arr[32]
            resp.format_result.add(ticket)
        }
        return resp
    }
}
