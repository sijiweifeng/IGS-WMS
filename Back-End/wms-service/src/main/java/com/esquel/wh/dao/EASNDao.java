package com.esquel.wh.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.esquel.wh.utils.*;
import com.esquel.wh.model.OrderModel;
import com.esquel.wh.model.ProductModel;
import com.esquel.wh.model.Stock;

public class EASNDao {

	public List<Stock> getEASNScanData() throws SQLException {
		ResultSet resultSet = null;
		List<Stock> stockList = new ArrayList<Stock>();
		Connection con = null;
		PreparedStatement statement = null;
		try {
			MSSqlUtil db = new MSSqlUtil("jdbc:sqlserver://192.168.7.112:1433;DatabaseName=EASN; ", "escmuser",
					"escmscan");
			con = db.getConnection();

			String sql = "select l.Line_ID as lineID, l.Reference_No as OrderNo,l.Buyer_PO_No as buyerPONO,l.Carton_No as cartonNo,l.UCC,l.UCCNo,l.SKU,l.Cust_Style_No as custStyleNo, "
					+ "l.Color_Code as colorCode,l.Size_Code as sizeCode,l.Size1_Code as size1Code,l.Size2_Code as size2Code,l.Qty as qty,l.Cut_No as cutNo, "
					+ " l.GOColor as ESCMColorCode,l.GOSize1 as ESCMSizeCode1,l.GOSize2 as ESCMSizeCode2,l.FR_SEWING_LINE as fristSewingLine,l.GmtGrd as grade, "
					+ " l.PACKING_LINE as packingLine,l.SewingFty as sewingFactory,l.FirstPackFty as firstPackFactory,l.LastPackFty as lastPackFactory "
					+ " from Auto_Receipt_HD h join Auto_Receipt_Lines l on h.HD_ID=l.HD_ID "
					+ " where trans_type='R' and h.Factory_CD='GEG' and h.hd_id >=5218396 and h.hd_id <=5218416 " ;

			statement = con.prepareStatement(sql);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {

				OrderModel order = new OrderModel();
				order.setOrderKey("OrderNo");
				order.setOrderNo(resultSet.getString("OrderNo"));

				ProductModel product = new ProductModel();
				product.setProductKey("custStyleNo;colorCode;sizeCode");
				product.setProductType("Garment");
				product.setCustStyleNo(resultSet.getString("custStyleNo"));
				product.setColorCode(resultSet.getString("colorCode"));
				product.setSizeCode(resultSet.getString("sizeCode"));
				product.setSize1Code(resultSet.getString("size1Code"));
				product.setSize2Code(resultSet.getString("size2Code"));
				product.setESCMColorCode(resultSet.getString("ESCMColorCode"));
				product.setESCMSizeCode1(resultSet.getString("ESCMSizeCode1"));
				product.setESCMSizeCode2(resultSet.getString("ESCMSizeCode2"));

				boolean addStock = true;
				for (Stock stock : stockList) {
					if (stock.getUCC().equals(resultSet.getString("UCC"))) {
						stock.getProduct().add(product);
						stock.setQty(stock.getQty() + resultSet.getInt("Qty"));
						addStock = false;
						break;
					}
				}
				if (addStock) {
					Stock stock = new Stock();
					stock.setStockKey("UCC");
					List<ProductModel> productList = new ArrayList<ProductModel>();
					productList.add(product);
					stock.setBuyerPONO(resultSet.getString("buyerPONO"));
					stock.setCartonNo(resultSet.getString("cartonNo"));
					stock.setUCC(resultSet.getString("UCC"));
					stock.setUCCNo(resultSet.getString("UCCNo"));
					stock.setSKU(resultSet.getString("SKU"));
					stock.setCutNo(resultSet.getString("cutNo"));
					stock.setGrade(resultSet.getString("grade"));
					stock.setFirstSewingLine(resultSet.getString("fristSewingLine") == null ? ""
							: resultSet.getString("fristSewingLine"));
					stock.setPackingLine(
							resultSet.getString("packingLine") == null ? "" : resultSet.getString("packingLine"));
					stock.setSewingFactory(
							resultSet.getString("sewingFactory") == null ? "" : resultSet.getString("sewingFactory"));
					stock.setFirstPackFactory(resultSet.getString("firstPackFactory") == null ? ""
							: resultSet.getString("firstPackFactory"));
					stock.setLastPackFactory(resultSet.getString("lastPackFactory") == null ? ""
							: resultSet.getString("lastPackFactory"));
					stock.setQty(resultSet.getInt("qty"));
					stock.setOrder(order);
					stock.setProduct(productList);
					stockList.add(stock);
				}

			}
			resultSet.close();
		} catch (SQLException e) {
			throw e;
		} finally {
			if (statement != null)
				statement.close();
			if (con != null)
				con.close();
		}
		return stockList;
	}
}
