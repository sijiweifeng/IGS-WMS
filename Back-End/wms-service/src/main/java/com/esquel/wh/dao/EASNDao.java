package com.esquel.wh.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.esquel.wh.model.OrderModel;
import com.esquel.wh.model.ProductModel;
import com.esquel.wh.model.Stock;
import com.esquel.wh.utils.MSSqlUtil;

public class EASNDao {

	public List<Stock> getEASNScanData() throws SQLException {
		ResultSet resultSet = null;
		List<Stock> stockList = new ArrayList<Stock>();
		Connection con = null;
		PreparedStatement statement = null;
		try {
			java.util.Date date = new java.util.Date();
			MSSqlUtil db = new MSSqlUtil("jdbc:sqlserver://192.168.7.112:1433;DatabaseName=EASN; ", "escmuser",
					"escmscan");
			con = db.getConnection();

			String sql = "select l.Line_ID as lineID, l.Reference_No as OrderNo,l.Buyer_PO_No as buyerPONO,l.Carton_No as cartonNo,l.UCC,l.UCCNo,l.SKU,l.Cust_Style_No as custStyleNo, "
					+ "l.Color_Code as colorCode,l.Size_Code as sizeCode,l.Size1_Code as size1Code,l.Size2_Code as size2Code,l.Qty as qty,l.Cut_No as cutNo, "
					+ " l.GOColor as ESCMColorCode,l.GOSize1 as ESCMSizeCode1,l.GOSize2 as ESCMSizeCode2,l.FR_SEWING_LINE as fristSewingLine,l.GmtGrd as grade, "
					+ " l.PACKING_LINE as packingLine,l.SewingFty as sewingFactory,l.FirstPackFty as firstPackFactory,l.LastPackFty as lastPackFactory, "
					+ " h.User_ID,ph.BuyID "
					+ " from Auto_Receipt_HD h join Auto_Receipt_Lines l on h.HD_ID=l.HD_ID "
					+ " left join AsnOrder o on l.cut_no = o.cutno and l.order_no = o.orderno "
					+ " left join POHead ph on o.pokey = ph.pokey "
					+ " where trans_type='R' and h.Factory_CD='GEG' and h.hd_id >=5218396 and h.hd_id <=5218416 " ;

			statement = con.prepareStatement(sql);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {

				OrderModel order = new OrderModel();
				order.setId(UUID.randomUUID().toString());
				order.setUniqueKey("OrderNo");
				order.setOrderNo(resultSet.getString("OrderNo"));
				order.setType("Garment");

				ProductModel product = new ProductModel();
				product.setCreatedBy("zhanghonl");				 
								
				product.setCreatedDate(new Date(date.getTime()));
				product.setCustColorCode(resultSet.getString("colorCode"));
				product.setCustSize1Code(resultSet.getString("size1Code"));
				product.setCustSize2Code(resultSet.getString("size2Code"));
				product.setCustSizeCode(resultSet.getString("sizeCode"));
				product.setEsqColorCode(resultSet.getString("ESCMColorCode"));
				product.setEsqSizeCode1(resultSet.getString("ESCMSizeCode1"));
				product.setEsqSizeCode2(resultSet.getString("ESCMSizeCode2"));
				product.setId(UUID.randomUUID().toString());
				product.setLastModifiedBy("zhanghonl");
				product.setLastModifiedDate(new Date(date.getTime()));
				product.setRevision(0);
				product.setSku(resultSet.getString("SKU"));
				product.setCustStyleNo(resultSet.getString("custStyleNo"));
				//product.setStyleNo(resultSet.getString("custStyleNo"));
				//product.setStyleRevNo(resultSet.getString(""));
				product.setUniqueKey("sku;custStyleNo");
				product.setUpc("");

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
					List<ProductModel> productList = new ArrayList<ProductModel>();
					productList.add(product);
					//stock.setBoardCode(boardCode);
					stock.setBuyerPONO(resultSet.getString("buyerPONO"));
					stock.setCartonNo(resultSet.getString("cartonNo"));
					stock.setCreatedBy("zhanghonl");
					stock.setCreatedDate(new Date(date.getTime()));
					stock.setCtNo(resultSet.getString("cartonNo"));
					stock.setEasnCustomerCd(resultSet.getString("BuyID"));
					//stock.setExpiryDate(expiryDate);
					stock.setFirstPackFactory(resultSet.getString("firstPackFactory"));
					stock.setFirstSewingLine(resultSet.getString("fristSewingLine"));
					stock.setGrade(resultSet.getString("grade"));
					stock.setId(UUID.randomUUID().toString());
					stock.setInFtyDate(new Date(date.getTime()));
					stock.setInStoreDate(new Date(date.getTime()));
					stock.setLastModifiedBy("zhanghonl");
					stock.setLastModifiedDate(new Date(date.getTime()));
					stock.setLastPackFactory(resultSet.getString("lastPackFactory"));
					stock.setOrder(order);
					stock.setOriginalQty(resultSet.getInt("qty"));
					stock.setPackingLine(resultSet.getString("packingLine"));
					stock.setPrePackCd("");
					stock.setPrePackStyle("");
					stock.setPrePackUnit("");
					stock.setProduct(productList);
					//stock.setQty(qty);
					stock.setSewingFactory(resultSet.getString("sewingFactory"));
					stock.setSource("E");
					stock.setStatus("D");
					stock.setUCC(resultSet.getString("ucc"));
					stock.setUniqueKey("ucc;inFtyDate;inStoreDate");
					stock.setUom("PCS");
					//stock.setWeight(weight);
					stock.setType("Garment");
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
