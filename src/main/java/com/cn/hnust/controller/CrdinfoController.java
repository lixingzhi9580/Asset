package com.cn.hnust.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cn.hnust.pojo.Crdinfo;
import com.cn.hnust.service.CrdinfoService;
import com.cn.hnust.service.MessageService;
import com.cn.hnust.utils.CommonsMethods;
import com.cn.iso8583.AsciiCodec;
import com.cn.iso8583.ConvHelper;
import com.cn.iso8583.Iso8583Parser;

@Controller
@RequestMapping("hunst/crainfo")
public class CrdinfoController {
	private static final Logger logger = LoggerFactory.getLogger(CrdinfoController.class);
	@Resource
	private CrdinfoService crdinfoService;
	
	@Resource
	private MessageService messageService;
	
	

	public List<String> readMsg(String fileName){
		List<String> list = new ArrayList<>();
		String charset = CommonsMethods.getFileCharset(fileName);  
		logger.info("charset:"+charset);
		try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName),Charset.forName(charset))) {
			list = br.lines().collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info("readMsgEnd");
		return list;
	}

	/**
	 * 前置
	 * @param list
	 */
	public Map<String,Crdinfo> parserMsg(List<String> list){
		logger.info("parserMsgStart");
		List<String> uuids=new ArrayList<>();
		List<String> removeuuid=new ArrayList<>();
		Map<String,Crdinfo> crdinfoMap=new HashMap<>();
		
		for(String lin:list){
			try {
				String[] lins=lin.split("\\|");
				String uuid=lins[1];
				if(uuids.contains(uuid)){
					crdinfoMap.remove(uuid);
					continue;
				}
				String msg=lins[2];
				if(msg.startsWith("解压后的报文")){
					msg=msg.replaceAll("解压后的报文:", "");
					byte[] reqbytesBack = ConvHelper.hexToByteArray(msg);
					Map<String, Object> bodyMap = Iso8583Parser.parse("pos", reqbytesBack);
					uuid=(String)bodyMap.get("F122");
					if(removeuuid.contains(uuid)){
						continue;
					}
					String IN_MOD=(String)bodyMap.get("IN_MOD");
					if(!("021".equals(IN_MOD)||"022".equals(IN_MOD))){
						removeuuid.add(uuid);
						continue;
					}
					
					Crdinfo crdinfo=new Crdinfo();
					crdinfo.setUuid(uuid);
					crdinfo.setTrack2(toTRACK((String) bodyMap.get("TRACK_2")));
					crdinfo.setTrack3(toTRACK((String) bodyMap.get("TRACK_3")));
					String pinData=(String) bodyMap.get("PIN_DATA");
					if(null!=pinData){
						String pin=new String(AsciiCodec.hexToByteArray(pinData));
						crdinfo.setPin(pin);
					}
					if(null!=crdinfo.getTrack2()&&crdinfo.getTrack2().indexOf("=")>0){
							String crdNo=crdinfo.getTrack2().substring(0,crdinfo.getTrack2().indexOf("="));
							crdinfo.setCrdno(crdNo);
					}else{
						crdinfoMap.remove(uuid);
						removeuuid.add(uuid);
					}
					String tran_date=lins[0];
					crdinfo.setTrandt(tran_date.substring(0,10));
					crdinfo.setTrantm(tran_date.substring(11));
					crdinfo.setUuid(uuid);
					crdinfoMap.put(crdinfo.getUuid(), crdinfo);
				}else if(msg.startsWith("响应报文")){
					if(removeuuid.contains(uuid)){
						continue;
					}
					msg=msg.replaceAll("响应报文:\\[", "").replaceAll("]", "");
					byte[] reqbytesBack = ConvHelper.hexToByteArray(msg);
					Map<String, Object> bodyMap = Iso8583Parser.parse("pos", reqbytesBack);
					String CPSCOD=(String)bodyMap.get("CPSCOD");
					if(!"00".equals(CPSCOD)){
						if(crdinfoMap.containsKey(uuid)){
							crdinfoMap.remove(uuid);
						}else{
							removeuuid.add(uuid);
						}
					}
				}
			} catch (Exception e) {
				logger.info(lin);
				logger.error("",e);
			}
		}
		logger.info("parserMsgEnd");
		return crdinfoMap;
	}
	
	public String toTRACK(String TRACK){
		if(null!=TRACK){
			TRACK=TRACK.replaceAll("D", "=");
		}
		return TRACK;
	}
	
	public void pressCrd(String fileName,String tofileName){
		List<String> msg=this.readMsg(fileName);
		Map<String,Crdinfo> crdinfoMap=this.parserMsg(msg);
		List<Crdinfo> crdinfo=new ArrayList<Crdinfo>(crdinfoMap.values());
		logger.info("写文件开始");
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(tofileName)),"UTF-8"));
            for (Crdinfo name : crdinfo) {
                bw.write(name.toString());
                bw.newLine();
            }
            bw.close();
        } catch (Exception e) {
            System.err.println("write errors :" + e);
        }
        logger.info("写文件结束");
		
		int i=crdinfo.size();
		int y=100000;
		while(i>y){
			List<Crdinfo> crdinfo1=crdinfo.subList(i-y, i);
			crdinfoService.insertBatch(crdinfo1);
			i=i-y;
		}
		List<Crdinfo> crdinfo2=crdinfo.subList(0, i);
		crdinfoService.insertBatch(crdinfo2);
	}
	
}