package study190303;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardService {
	
	public List<BoardInfoVO> selectBoard() {
		String sql = "select * from cat";
		try {
			PreparedStatement ps = DBCon.getCon().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			List<BoardInfoVO> biList = new ArrayList<>();
			while(rs.next()) {
				BoardInfoVO bi = new BoardInfoVO();
				bi.setNum(rs.getInt("num"));
				bi.setTitle(rs.getString("title"));
				bi.setContent(rs.getString("content"));
				bi.setCredat(rs.getString("credat"));
				bi.setCretim(rs.getString("cretim"));
				bi.setCnt(rs.getInt("cnt"));
				bi.setIsactive(rs.getString("isactive"));
				biList.add(bi);
			}
			return biList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void insertBoard(String title, String content) {
		String sql = "insert into cat(num,title,content,credat,cretim)" +
				" values(seq_num.nextval,?,?,to_char(sysdate,'YYYYMMDD')," +
				" to_char(sysdate,'HH24MISS'))";
		try {
			PreparedStatement ps = DBCon.getCon().prepareStatement(sql);
			ps.setString(1, title);
			ps.setString(2, content);
			int cnt = ps.executeUpdate();
			if(cnt==1) {
				System.out.println(" 게시글이 정상적으로 등록되었습니다. ");
			} else if (cnt==0) {
				System.out.println(" 오류로 게시글이 등록되지 않았습니다. ");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateBoard(String strNum, String title, String content) {
		String sql = "update cat set title=?," +
				" content=? where num=?";
		try {
			PreparedStatement ps = DBCon.getCon().prepareStatement(sql);
			int num = Integer.parseInt(strNum);
			ps.setInt(3, num);
			ps.setString(1, title);
			ps.setString(2, content);
			int cnt = ps.executeUpdate();
			if(cnt==1) {
				System.out.println(num + "번 게시글이 정상적으로 수정되었습니다.");
			} else if(cnt==0) {
				System.out.println(" 게시글 번호를 다시 확인해주세요. ");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void deleteBoard(String strNum) {
		String sql = "delete from cat where num=?";
		try {
			PreparedStatement ps = DBCon.getCon().prepareStatement(sql);
			int num = Integer.parseInt(strNum);
			ps.setInt(1, num);
			int cnt = ps.executeUpdate();
			if(cnt==1) {
				System.out.println(num + "번 게시글이 정상적으로 삭제되었습니다.");
			} else if (cnt==0) {
				System.out.println(" 이미 삭제된 게시글입니다. ");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
