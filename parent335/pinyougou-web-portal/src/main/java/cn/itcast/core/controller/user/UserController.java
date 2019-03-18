package cn.itcast.core.controller.user;

import org.apache.poi.xssf.usermodel.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@RestController
@RequestMapping("/user")
public class UserController {

    //文件下载：导出excel表
    @RequestMapping("/exportExcel.do")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        //一、从后台拿数据
        if (request == null || response==null  )
        {
            return;
        }
        //List<VipConsumes> list = null;
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        int consumesType = Integer.parseInt(request.getParameter("consumesType"));
        int conPaymentStatus =Integer.parseInt(request.getParameter("conPaymentStatus"));

 /*       VipConsumesExample example = new VipConsumesExample();
        if(consumesType!=0 && conPaymentStatus!=0){
            example.createCriteria().andTimeBetween(startTime, endTime).andConsumeTypeEqualTo(consumesType).andStatusEqualTo(conPaymentStatus);
        }else if(consumesType ==0 && conPaymentStatus!=0) {
            example.createCriteria().andTimeBetween(startTime, endTime).andStatusEqualTo(conPaymentStatus);
        }else if(consumesType!=0 && conPaymentStatus==0){
            example.createCriteria().andTimeBetween(startTime, endTime).andConsumeTypeEqualTo(consumesType);
        }else {
            example.createCriteria().andTimeBetween(startTime, endTime);
        }
        list = this.vipConsumesDao.selectByExample(example);*/
        //二、 数据转成excel
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/x-download");

        String fileName = "消费记录.xlsx";
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        // 第一步：定义一个新的工作簿
        XSSFWorkbook wb = new XSSFWorkbook();
        // 第二步：创建一个Sheet页
        XSSFSheet sheet = wb.createSheet("startTimeendTime");
        sheet.setDefaultRowHeight((short) (2 * 256));//设置行高
        sheet.setColumnWidth(0, 4000);//设置列宽
        sheet.setColumnWidth(1,5500);
        sheet.setColumnWidth(2,5500);
        sheet.setColumnWidth(3,5500);
        sheet.setColumnWidth(11,3000);
        sheet.setColumnWidth(12,3000);
        sheet.setColumnWidth(13,3000);
        XSSFFont font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 16);

        XSSFRow row = sheet.createRow(0);
        XSSFCell cell = row.createCell(0);
        cell.setCellValue("流水号 ");
        cell = row.createCell(1);
        cell.setCellValue("微信名 ");
        cell = row.createCell(2);
        cell.setCellValue("微信订单号");
        cell = row.createCell(3);
        cell.setCellValue("消费时间");
        cell = row.createCell(4);
        cell.setCellValue("消费类型");
        cell = row.createCell(5);
        cell.setCellValue("剩余积分 ");
        cell = row.createCell(6);
        cell.setCellValue("新增积分 ");
        cell = row.createCell(7);
        cell.setCellValue("扣除积分 ");
        cell = row.createCell(8);
        cell.setCellValue("消费金额");
        cell = row.createCell(9);
        cell.setCellValue("支付方式");
        cell = row.createCell(10);
        cell.setCellValue("支付状态 ");
        cell = row.createCell(11);
        cell.setCellValue("钱包原始金额");
        cell = row.createCell(12);
        cell.setCellValue("钱包扣除金额");
        cell = row.createCell(13);
        cell.setCellValue("钱包剩余金额");

        XSSFRow rows;
        XSSFCell cells;
      /*  for (int i = 0; i < list.size(); i++) {
            // 第三步：在这个sheet页里创建一行
            rows = sheet.createRow(i+1);
            // 第四步：在该行创建一个单元格
            cells = rows.createCell(0);
            // 第五步：在该单元格里设置值
            cells.setCellValue(list.get(i).getConsumeId());

            cells = rows.createCell(1);
            cells.setCellValue(list.get(i).getName());
            cells = rows.createCell(2);
            cells.setCellValue(list.get(i).getOrderNumber());
            cells = rows.createCell(3);
            cells.setCellValue(list.get(i).getTime());
            cells = rows.createCell(4);
            if (list.get(i).getConsumeType() == 2) {
                cells.setCellValue("酒水零食费");

            } else {
                cells.setCellValue("报名费");
            }
            cells = rows.createCell(5);
            cells.setCellValue(list.get(i).getIntegral());
            cells = rows.createCell(6);
            cells.setCellValue(list.get(i).getIntegralIn());
            cells = rows.createCell(7);
            cells.setCellValue(list.get(i).getIntegralOut());
            cells = rows.createCell(8);
            cells.setCellValue(list.get(i).getMoney());
            cells = rows.createCell(9);
            if (list.get(i).getPayment() == 2) {
                cells.setCellValue("积分抵现");
            } else if (list.get(i).getPayment() == 3) {
                cells.setCellValue("微信支付");
            } else if (list.get(i).getPayment() == 4) {
                cells.setCellValue("现金");
            } else if (list.get(i).getPayment() == 1) {
                cells.setCellValue("钱包");
            }
            cells = rows.createCell(10);
            if (list.get(i).getStatus() == 2) {
                cells.setCellValue("已支付");
            } else if (list.get(i).getStatus() == 1) {
                cells.setCellValue("未支付");
            }
            cells = rows.createCell(11);
            cells.setCellValue(list.get(i).getWalletOriginal());
            cells = rows.createCell(12);
            cells.setCellValue(list.get(i).getWalletOut());
            cells = rows.createCell(13);
            cells.setCellValue(list.get(i).getWalletSurplus());
        }
*/
        try {
            OutputStream out = response.getOutputStream();
            wb.write(out);
            out.close();
            wb.close();
        } catch (IOException e) {

            e.printStackTrace();
        }

    }


}
