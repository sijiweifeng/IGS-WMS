package com.esquel.wh.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.esquel.wh.utils.*;
import com.esquel.wh.model.FabricProductModel;
import com.esquel.wh.model.FabricStock;
import com.esquel.wh.model.OrderModel;

public class GEKFabricDao {
	public List<FabricStock> getGEKFabricData() throws SQLException {
		ResultSet resultSet = null;
		List<FabricStock> stockList = new ArrayList<FabricStock>();
		Connection con = null;
		PreparedStatement statement = null;
		try {
			MSSqlUtil db = new MSSqlUtil("jdbc:sqlserver://192.168.7.60:1433;DatabaseName=FabricStoreDB; ", "STMRead",
					"readerluo");
			con = db.getConnection();

			String sql = "select out_id as LineID,ppo_no as OrderNo,barcode as UCC,combo_name as comboName,size_code as sizeCode,gek_lot_no as gekLotNo, "
					+ " item_code as itemCode,item_desc as itemDesc,grade ,shade,supplier_lot_no as supplierLotNo,width, "
					+ " qty ,weight,foc_qty as focQty,allowance_qty as allowanceQty,stock_uom as stockUom, "
					+ " invoice_price as invoicePrice,invoice_no as invoiceNo,gross_price as grossPrice,ppo_price as ppoPrice "
					+ " ,currency,Fabric_Code as fabricCode,eSCM_seq as eSCMSeq "
					+ " from uvw_fsoutforscm 	where out_id>=20603765 and out_id<=20603787 ";

			statement = con.prepareStatement(sql);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {

				OrderModel order = new OrderModel();
				order.setUniqueKey("OrderNo");
				order.setOrderNo(resultSet.getString("OrderNo"));

				FabricProductModel product = new FabricProductModel();
				product.setProductKey(
						"comboName;sizeCode;gekLotNo;itemCode;grade;shade;supplierLotNo;width;fabricCode,eSCMSeq");
				product.setProductType("Fabric");
				product.setComboName(resultSet.getString("comboName"));
				product.seteSCMSeq(resultSet.getLong("eSCMSeq"));
				product.setFabricCode(resultSet.getString("fabricCode"));
				product.setGekLotNo(resultSet.getString("gekLotNo"));
				product.setGrade(resultSet.getString("grade"));
				product.setItemCode(resultSet.getString("itemCode"));
				product.setItemDesc(resultSet.getString("itemDesc"));
				product.setShade(resultSet.getString("shade"));
				product.setSizeCode(resultSet.getString("sizeCode"));
				product.setSupplierLotNo(resultSet.getString("supplierLotNo"));
				product.setWidth(resultSet.getString("width"));

				boolean addStock = true;
				for (FabricStock stock : stockList) {
					if (stock.getUCC().equals(resultSet.getString("UCC"))) {
						stock.getProduct().add(product);
						stock.setQty(stock.getQty().add(resultSet.getBigDecimal("Qty")));
						addStock = false;
						break;
					}
				}
				if (addStock) {
					FabricStock stock = new FabricStock();
					List<FabricProductModel> productList = new ArrayList<FabricProductModel>();
					productList.add(product);
					stock.setAllowance_qty(resultSet.getBigDecimal("allowanceQty"));
					stock.setCurrency(resultSet.getString("currency"));
					stock.setFoc_qty(resultSet.getBigDecimal("focQty"));
					stock.setGrossPrice(resultSet.getBigDecimal("grossPrice"));
					stock.setInvoiceNo(resultSet.getString("invoiceNo"));
					stock.setInvoicePrice(resultSet.getBigDecimal("invoicePrice"));
					stock.setOrder(order);
					stock.setPpoPrice(resultSet.getBigDecimal("ppoPrice"));
					stock.setProduct(productList);
					stock.setQty(resultSet.getBigDecimal("qty"));
					stock.setStockKey("UCC");
					stock.setStockUom(resultSet.getString("stockUom"));
					stock.setUCC(resultSet.getString("UCC"));
					stock.setWeight(resultSet.getBigDecimal("weight"));
					stockList.add(stock);
				}

			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
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
