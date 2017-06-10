package com.ben.fdam_ver_020.utils;

import android.content.Context;
import com.ben.fdam_ver_020.bean.Device;
import com.ben.fdam_ver_020.bean.Staff;
import com.ben.fdam_ver_020.database.StaffDaoImpl;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Parser {

    @SuppressWarnings ("deprecation")
    public static List<Device> readFromExcel(InputStream file, Context context) throws IOException {

        HSSFWorkbook myExcelBook = new HSSFWorkbook(file);

        myExcelBook.isHidden();

        HSSFSheet myExcelSheet = myExcelBook.getSheet("Ищенко Тарас");

        ArrayList<Device> list = new ArrayList<>();

        Iterator iterator = myExcelSheet.rowIterator();

        StaffDaoImpl staffDao = new StaffDaoImpl(context);

        while (iterator.hasNext()) {

            HSSFRow row = (HSSFRow) iterator.next();

            Device bean = new Device();

            try {

                if (row.getCell(0).getCellType() == HSSFCell.CELL_TYPE_BLANK) {

                    break;

                }else {

                    bean.setDevice_id((int) row.getCell(0).getNumericCellValue());
                    bean.setDevice_name(row.getCell(1).getStringCellValue());
                    bean.setDevice_location(row.getCell(2).getStringCellValue());

                    String staff_name = row.getCell(4).getStringCellValue();

                    long staff_id = 0;

                    if (!staff_name.equalsIgnoreCase("")) {

                        ArrayList<Staff> staffs = staffDao.getStaff();

                        boolean exist = false;

                        if (staffs.size() > 0) {
                            for (Staff e : staffs) {
                                if (staff_name.equalsIgnoreCase(e.getStaff_last_name())) {
                                    staff_id = e.getStaff_id();
                                    bean.setStaff_id((int) staff_id);
                                    exist = true;
                                    break;
                                }
                            }

                            if (!exist) {
                                Staff staff = new Staff();
                                staff.setStaff_last_name(staff_name);
                                staff.setStaff_name("");
                                staff.setStaff_phone("");
                                staff.setStaff_manager("");

                                staff_id = staffDao.addStaff(staff);
                                bean.setStaff_id((int) staff_id);
                            }

                        }else {
                            Staff staff = new Staff();
                            staff.setStaff_last_name(staff_name);
                            staff.setStaff_name("");
                            staff.setStaff_phone("");
                            staff.setStaff_manager("");

                            staff_id = staffDao.addStaff(staff);
                            bean.setStaff_id((int) staff_id);
                        }

                    }else {
                        bean.setStaff_id(0);
                    }

                    bean.setDevice_old_phone(String.valueOf(row.getCell(5).getNumericCellValue()));
                    bean.setDevice_new_phone(String.valueOf(row.getCell(6).getNumericCellValue()));

                    list.add(bean);
                }

            }catch (Exception e) {e.printStackTrace();}

            myExcelBook.close();
        }

        return list;
    }

    @SuppressWarnings ("deprecation")
    public static void writeIntoExcel(String file) throws FileNotFoundException, IOException {

    Workbook book = new HSSFWorkbook();

    Sheet sheet = book.createSheet("Birthdays");

    // Нумерация начинается с нуля
    Row row = sheet.createRow(0);

    // Мы запишем имя и дату в два столбца
    // имя будет String, а дата рождения ­­­ Date,
    // формата dd.mm.yyyy
    Cell name = row.createCell(0);
        name.setCellValue("John");

    Cell birthdate = row.createCell(1);

    DataFormat format = book.createDataFormat();
    CellStyle dateStyle = book.createCellStyle();
        dateStyle.setDataFormat(format.getFormat("dd.mm.yyyy"));
        birthdate.setCellStyle(dateStyle);


    // Нумерация лет начинается с 1900­го
        birthdate.setCellValue(new Date(110, 10, 10));

    // Меняем размер столбца
        sheet.autoSizeColumn(1);

    // Записываем всё в файл

     book.write(new FileOutputStream(file));
       book.close();
   }

}
