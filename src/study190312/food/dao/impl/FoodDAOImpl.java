package study190312.food.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import study190312.food.dao.FoodDAO;
import study190312.food.db.DBConnection;
import study190312.food.vo.FoodVO;

public class FoodDAOImpl implements FoodDAO {

	@Override
	public List<FoodVO> selectFoodList(FoodVO food) {
		String sql = "select food_num,food_name,food_price " + " from food where 1=1";
		if (food != null) {
			if (food.getFoodName() != null) {
				sql += " and food_name=?";
			}
		}
		try {
			PreparedStatement ps = DBConnection.getCon().prepareStatement(sql);
				if (food.getFoodName() != null) {
					ps.setString(1, food.getFoodName());
				}
			ResultSet rs = ps.executeQuery();
			List<FoodVO> foodList = new ArrayList<>();
			while(rs.next()) {
				FoodVO sFood = new FoodVO();
				sFood.setFoodNum(rs.getInt("food_num"));
				sFood.setFoodName(rs.getString("food_name"));
				sFood.setFoodPrice(rs.getInt("food_price"));
				foodList.add(sFood);
			}
			return foodList;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close();
		}
		return null;
	}

	@Override
	public FoodVO selectFood(Integer foodNum) {
		String sql = "select food_num, food_name, food_price from food";
		sql += " where food_num=?";
		try {
			PreparedStatement ps = DBConnection.getCon().prepareStatement(sql);
			ps.setInt(1, foodNum);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				FoodVO food = new FoodVO();
				food.setFoodNum(rs.getInt("food_num"));
				food.setFoodName(rs.getString("food_name"));
				food.setFoodPrice(rs.getInt("food_price"));
				return food;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close();
		}
		return null;
	}

	@Override
	public int updateFood(FoodVO food) {
		String sql = "update food set food_name=?, food_price=? where food_num=?";
		try {
			PreparedStatement ps = DBConnection.getCon().prepareStatement(sql);
			ps.setString(1, food.getFoodName());
			ps.setInt(2, food.getFoodPrice());
			ps.setInt(3, food.getFoodNum());
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close();
		}
		return 0;
	}

	@Override
	public int deleteFood(FoodVO food) {
		String sql = "delete from food where food_num=?";
		try {
			PreparedStatement ps = DBConnection.getCon().prepareStatement(sql);
			ps.setInt(1,food.getFoodNum());
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close();
		}
		return 0;
	}

	@Override
	public int insertFood(FoodVO food) {
		String sql = "insert into food(food_num,food_name,food_price) " +
				" values((select nvl(max(food_num),0)+1 from food),?,?)";
		try {
			PreparedStatement ps = DBConnection.getCon().prepareStatement(sql);
			ps.setString(1, food.getFoodName());
			ps.setInt(2, food.getFoodPrice());
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close();
		}
		return 0;
	}
}
