///**  
// * All rights Reserved, Designed By Suixingpay.
// * @author: lixingzhi[li_xz1@suixingpay.com] 
// * @date: 2017年6月10日 下午5:05:00   
// * @Copyright ©2017 Suixingpay. All rights reserved. 
// * 注意：本内容仅限于随行付支付有限公司内部传阅，禁止外泄以及用于其他的商业用途。
// */
//package sig;
//
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayInputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Base64;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.imageio.ImageIO;
//
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.google.gson.Gson;
//
//
//
////SELECT
////  '"{'
////  ||'uuid='''
////  ||uuid
////  ||''',mno='''
////  ||mno
////  ||''',trmNo='''
////  ||trmNo
////  ||''',posOprId='''
////  ||posOprId
////  ||''',crdNo='''
////  ||crdNo
////  ||''',batNo='''
////  ||batNo
////  ||''',posSeqNo='''
////  ||posSeqNo
////  ||''',tranDt='''
////  ||tranDt
////  ||''',tranTm='''
////  ||tranTm
////  ||''',relReNo='''
////  ||relReNo
////  ||''',tranCd='''
////  ||'消费'
////  ||''',tranAmt='''
////  ||tranAmt
////  ||''',bnkNm='''
////  ||bnkNm
////  ||''',autCd='''
////  ||autCd
////  ||''',mecDisNm='''
////  ||mecDisNm
////  ||'''}",'
////FROM
////  (
////    SELECT
////      pt.uuid,
////      case 
////when pct.corg_merc_cd='836100048140222' then '中国联通（中关村营业厅）'
////when pct.corg_merc_cd='836100048140223' then '中国联通（花园路营业厅）'
////when pct.corg_merc_cd='836100048140224' then '中国联通（呼家楼营业厅）'
////when pct.corg_merc_cd='836100048140225' then '中国联通（望京营业厅)'
////when pct.corg_merc_cd='836100048140226' then '中国联通（新中西里营业厅）'
////when pct.corg_merc_cd='836100048140227' then '中国联通（东城区营业厅）'
////when pct.corg_merc_cd='836100048140228' then '中国联通（紫竹园营业厅）'
////when pct.corg_merc_cd='836100048140229' then '中国联通（丰台区营业厅）'
////when pct.corg_merc_cd='836100048140230' then '中国联通(公主坟营业厅)'
////when pct.corg_merc_cd='836100048140221' then '中国联通（西直门店）'
////end  AS mecDisNm,
////      pct.corg_merc_cd AS mno,
////      pt.trm_no        AS trmNo,
////      pt.pos_opr_id    AS posOprId,
////      (
////        SELECT
////          bnk_nm
////        FROM
////          pts.t_pts_bin bin
////        WHERE
////          bin.fit_no=pt.bin_id
////      )             AS bnkNm,
////      pt.crd_no     AS crdNo,
////      pt.bat_no     AS batNo,
////      pt.pos_seq_no AS posSeqNo,
////      pt.tran_dt    AS tranDt,
////      pt.tran_tm    AS tranTm,
////      pt.ret_re_no  AS relReNo,
////      pt.aut_cd     AS autCd,
////      pt.tran_cd    AS tranCd,
////      pt.tran_amt   AS tranAmt
////    FROM
////      pts.t_pts_trandata pt
////    LEFT JOIN pts.t_pts_card_valid v
////    ON
////      pt.uuid=v.uuid
////    LEFT JOIN PTS.T_PTS_ESIGNATURE pe
////    ON
////      pe.uuid=pt.uuid
////    LEFT JOIN pts.t_pts_bjcup_trandata pct
////    ON
////      pt.uuid=pct.uuid
////  )
////  t
////WHERE
////  t.uuid IN ( '3ce960299e884238ad0a08714072f123',
////  'c857c8a49c644e95bc5d78aafb0fd3ae', 'd52e18ff650c4137881f6c56603c7c5e',
////  'fd7fa64772a14350839861b760ad0d2c' );
//
///**
// * TODO
// * 
// * @author: lixingzhi[li_xz1@suixingpay.com]
// * @date: 2017年6月10日 下午5:05:00
// * @version: V1.0
// * @review: lixingzhi[li_xz1@suixingpay.com]/2017年6月10日 下午5:05:00
// */
//public class LogAnalysed {
//    private final static Logger LOG = LoggerFactory.getLogger(LogAnalysed.class);
//    public static void main(String[] args) throws IOException {
//    	String aa="===	阳曲县福乐超市";
//    	System.out.println(aa);
//    	aa=aa.replaceAll("\t", "");
//    	System.out.println(aa);
//    }
//
//    public static void aa(String arr) throws IOException {
//        Map<String,Object> map =new HashMap<>();
//        map=new Gson().fromJson(arr, map.getClass());
//        String path="D:/Work/eclipse-jee-luna-SR1-win32-x86_64/workspace_bap/bap/bap/src/main/webapp/scripts/app/pts/";
//        File f=new File(path + "/e-signature.png");
//        if(f.exists()){
//            boolean fl=f.delete();
//            LOG.debug("删除文件-->>f.delete()执行结果为："+fl);
//        }
//
//        String fileName = "C:/Users/行知/Downloads/"+map.get("uuid")+".txt";
//        LOG.info("图片目录：[{}]", fileName);
//        // 读取图片字节数组
//        InputStream ini = new FileInputStream(fileName);
//        byte[]  data = new byte[ini.available()];
//            ini.read(data);
//            ini.close();
//        String pic = new String(data,"utf-8");
//
//        //String pic="base64=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";
//        pic = pic.substring(7);
//        byte[] bytes = Base64.getDecoder().decode(pic.getBytes());
//
//        ByteArrayInputStream in = new ByteArrayInputStream(bytes); // 将b作为输入流；
//        BufferedImage image2 = ImageIO.read(in);
//        // 将in作为输入流，读取图片存入image中，而这里in可以为ByteArrayInputStream();
//        ImageIO.write(image2, "png", new File(path + "/singpicture.png"));
//        File originalImage = new File(path + "/singpicture.png");
//
//        HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
//        StringBuffer sb = new StringBuffer();
//
//        sb.append("<table width='227' border='0' align='center' cellpadding='0' cellspacing'0'>");
//        sb.append("<tr style='background-color:#000; text-align:center;'>");
//        sb.append("<td colspan='2'><img src='file:///D:/Work/eclipse-jee-luna-SR1-win32-x86_64/workspace_bap/bap/bap/src/main/webapp/scripts/app/pts/title.jpg' alt='随行付' width='227' height='50' /></td>");
//        sb.append("</tr>");
//        sb.append("<tr>");
//        sb.append("<td colspan='2' style='text-align:center;padding:2px;'>---------------商户存根--------------</td>");
//        sb.append("</tr>");
//        sb.append("<tr>");
//        sb.append("<td colspan='2' style='padding:2px;'>商户名称：</td>");
//        sb.append("</tr>");
//        sb.append("<tr>");
//        sb.append("<td colspan='2' style='font-size:15px;text-indent:12px;padding:2px;'>"+(map.get("mecDisNm")==null?"":map.get("mecDisNm").toString())+"</td>");
//        sb.append("</tr>");
//        sb.append("<tr>");
//        sb.append("<td colspan='2' style='padding:2px;'>商户号：</td>");
//        sb.append("</tr>");
//        sb.append("<tr>");
//        sb.append("<td colspan='2' style='font-size:14px;text-indent:12px;padding:2px;'>"+(map.get("mno")==null?"":map.get("mno").toString())+"</td>");
//        sb.append("</tr>");
//        sb.append("<tr>");
//        sb.append("<td width='127' style='padding:2px;'>终端号：</td>");
//        sb.append("<td width='100' style='padding:2px;'>操作员：</td>");
//        sb.append("</tr>");
//        sb.append("<tr>");
//        sb.append("<td style='font-size:14px;text-indent:12px;'>"+(map.get("trmNo")==null?"":map.get("trmNo").toString())+"</td>");
//        sb.append("<td align='right' style='font-size:14px;text-indent:12px;padding:2px;'>"+(map.get("posOprId")==null?"":map.get("posOprId").toString())+"</td>");
//        sb.append("</tr>");
//        sb.append("<tr>");
//        sb.append("<td colspan='2' style='text-align:center;'>-------------------------------------</td>");
//        sb.append("</tr>");
//        sb.append("<tr>");
//        sb.append("<td style='padding:2px;'>发卡方：</td>");
//        sb.append("<td style='padding:2px;'>有效期：</td>");
//        sb.append("</tr>");
//        sb.append("<tr>");
//        sb.append("<td style='font-size:14px;text-indent:12px;padding:2px;'>"+(map.get("bnkNm")==null?"":map.get("bnkNm").toString())+"</td>");
//        sb.append("<td align='right' style='font-size:14px;text-indent:12px;padding:2px;'>"+(map.get("expDt")==null?"":map.get("expDt").toString())+"</td>");
//        sb.append("</tr>");
//        sb.append("<tr>");
//        sb.append("<td colspan='2' style='padding:2px;'>卡号：</td>");
//        sb.append("</tr>");
//        sb.append("<tr>");
//        sb.append("<td colspan='2' style='font-size:18px; text-indent:12px; padding:4px;'>"+(map.get("crdNo")==null?"":map.get("crdNo").toString())+"</td>");
//        sb.append("</tr>");
//        sb.append("<tr>");
//        sb.append("<td style='padding:2px;'>批次号：</td>");
//        sb.append("<td style='padding:2px;'>流水号：</td>");
//        sb.append("</tr>");
//        sb.append("<tr>");
//        sb.append("<td style='font-size:14px;text-indent:12px;padding:2px;'>"+(map.get("batNo")==null?"":map.get("batNo").toString())+"</td>");
//        sb.append("<td align='right' style='font-size:14px;text-indent:12px;padding:2px;'>"+(map.get("posSeqNo")==null?"":map.get("posSeqNo").toString())+"</td>");
//        sb.append("</tr>");
//        sb.append("<tr>");
//        sb.append("<td style='padding:2px;'>交易日期：</td>");
//        sb.append("<td style='padding:2px;'>交易时间：</td>");
//        sb.append("</tr>");
//        sb.append("<tr>");
//        sb.append("<td style='font-size:14px;text-indent:12px;padding:2px;'>"+(map.get("tranDt")==null?"":map.get("tranDt").toString())+"</td>");
//        sb.append("<td align='right' style='font-size:14px;text-indent:12px;padding:2px;'>"+(map.get("tranTm")==null?"":map.get("tranTm").toString())+"</td>");
//        sb.append("</tr>");
//        if(!StringUtils.isEmpty(map.get("OUUID")==null?"":map.get("OUUID").toString())){
//            sb.append("<tr>");
//            sb.append("<td style='padding:2px;'>原批次号：</td>");
//            sb.append("<td style='padding:2px;'>原流水号：</td>");
//            sb.append("</tr>");
//            sb.append("<tr>");
//            sb.append("<td style='font-size:14px;text-indent:12px;padding:2px;'>"+(map.get("obatNo")==null?"":map.get("obatNo").toString())+"</td>");
//            sb.append("<td align='right' style='font-size:14px;text-indent:12px;padding:2px;'>"+(map.get("oposSeqNo")==null?"":map.get("oposSeqNo").toString())+"</td>");
//            sb.append("</tr>");
//            sb.append("<tr>");
//            sb.append("<td style='padding:2px;'>原交易类型：</td>");
//            sb.append("<td style='padding:2px;'>原交易日期：</td>");
//            sb.append("</tr>");
//            sb.append("<tr>");
//            sb.append("<td style='font-size:10px;text-indent:11px;padding:1px;'>"+(map.get("otranCd")==null?"":map.get("otranCd").toString())+"</td>");
//            sb.append("<td align='right' style='font-size:14px;text-indent:12px;padding:1px;'>"+(map.get("otranDt")==null?"":map.get("otranDt").toString())+"</td>");
//            sb.append("</tr>");
//        }
//        sb.append("<tr>");
//        sb.append("<td style='padding:2px;'>检索参考号：</td>");
//        sb.append("<td style='padding:2px;'>");
//        if(!StringUtils.isEmpty((map.get("autCd")==null?"":map.get("autCd").toString()))){
//            sb.append("授权码：");
//        }
//        sb.append("</td>");
//        sb.append("</tr>");
//        sb.append("<tr>");
//        sb.append("<td style='font-size:14px;text-indent:12px;padding:2px;'>"+map.get("relReNo")+"</td>");
//        sb.append("<td align='right' style='font-size:14px;text-indent:12px;padding:2px;'>"+(map.get("autCd")==null?"":map.get("autCd").toString())+"</td>");
//        sb.append("</tr>");
//        sb.append("<tr>");
//        sb.append(" <td colspan='2' style='text-align:center;'>-------------------------------------</td>");
//        sb.append("</tr>");
//        sb.append("<tr>");
//        sb.append("<td colspan='2'  style='font-size:20px; text-indent:12px; padding:4px;' >"+(map.get("tranCd")==null?"":map.get("tranCd").toString())+"</td>");
//        sb.append("</tr>");
//        sb.append("<tr>");
//        sb.append("<td colspan='2' style='padding:2px;'>金额：</td>");
//        sb.append("</tr>");
//        sb.append("<tr>");
//        sb.append("<td colspan='2'  style='font-size:20px; text-indent:12px; padding:4px;' >RMB&nbsp;&nbsp;"+(map.get("tranAmt")==null?"":map.get("tranAmt").toString())+" </td>");
//        sb.append("</tr>");
//        sb.append("<tr>");
//        sb.append("<td colspan='2' style='text-align:center;'>-------------------------------------</td>");
//        sb.append("</tr>");
//        sb.append("<tr>");
//        sb.append("<td colspan='2' style='padding:2px;'>持卡人签名：</td>");
//        sb.append("</tr>");
//        sb.append("<tr>");
//        sb.append("<td colspan='2'>");
//        boolean isVvWallet = map.get("isVvWallet") == null ? false : (boolean) map.get("isVvWallet");
//
//        sb.append("<img src='file:///"+path+"/singpicture.png' alt='持卡人签名' id='img' width='227'/>");
//        sb.append("</td>");
//        sb.append("</tr>");
//        sb.append("<tr>");
//        sb.append("<td colspan='2' style='text-align:center;padding:2px;'>---------------商户存根--------------</td>");
//        sb.append("</tr>");
//        sb.append("<tr>");
//        sb.append("<td colspan='2' style='padding:2px;'>本人同意支付上述款项</td>");
//        sb.append("</tr>");
//        sb.append("<tr>");
//        sb.append("<td colspan='2'>&nbsp;</td>");
//        sb.append("</tr>");
//        sb.append("<tr>");
//        sb.append("<td colspan='2' style='padding:2px;'>");
//        sb.append("<p>央行授予随行付支付业务许可经营</p><p>牌照编号：<span>Z2011711000016</span></p><p>全国客服电话：<span>4000-887-626</span></p>");
//        sb.append("</td></tr></table>");
//
//        imageGenerator.loadHtml(new String(sb.toString().getBytes(),"UTf-8"));
//        imageGenerator.getBufferedImage();
//        imageGenerator.saveAsImage(path + "/"+map.get("mno")+"-"+map.get("relReNo")+".png");
//        System.out.println(path + "/"+map.get("mno")+"-"+map.get("relReNo")+".png");
//    }
//
//
//
//
//    private static byte[] str2Bcd(String asc) {
//
//        // 原数据的长度
//        int len = asc.length();
//        int mod = len % 2;
//
//        if (mod != 0) {
//            asc = asc + "0";
//            len = asc.length();
//        }
//
//        // 原数据
//        byte bOriginalData[] = new byte[len];
//        if (len >= 2) {
//            len = len / 2;
//        }
//
//        // 将字符串数据转换成字节数据
//        bOriginalData = asc.getBytes();
//
//        // 转换后的BCD码
//        byte bBCD[] = new byte[len];
//
//        int sH, sL;
//
//        for (int p = 0; p < asc.length() / 2; p++) {
//
//            if ((bOriginalData[2 * p] >= 'a') && (bOriginalData[2 * p] <= 'f')) {
//                sH = bOriginalData[2 * p] - 'a' + 10;
//            } else if ((bOriginalData[2 * p] >= 'A')
//                    && (bOriginalData[2 * p] <= 'F')) {
//                sH = bOriginalData[2 * p] - 'A' + 10;
//            } else {
//                sH = bOriginalData[2 * p] & 0x0f;
//            }
//
//            if ((bOriginalData[2 * p + 1] >= 'a')
//                    && (bOriginalData[2 * p + 1] <= 'f')) {
//                sL = bOriginalData[2 * p + 1] - 'a' + 10;
//            } else if ((bOriginalData[2 * p + 1] >= 'A')
//                    && (bOriginalData[2 * p + 1] <= 'F')) {
//                sL = bOriginalData[2 * p + 1] - 'A' + 10;
//            } else {
//                sL = bOriginalData[2 * p + 1] & 0x0f;
//            }
//
//            bBCD[p] = (byte) ((sH << 4) + sL);
//        }
//        return bBCD;
//    }
//
//}
