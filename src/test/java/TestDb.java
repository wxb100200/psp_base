import com.base.am.model.Account;
import com.base.am.util.EbeanUtil;

import java.util.List;

/**
 * Created by wxb on 2017/1/10.
 */
public class TestDb {
    public  static  void main(String[] args){
        List<Account> accounts= EbeanUtil.find(Account.class).where().findList();
        for(Account a:accounts){
            System.out.println("loginName:"+a.getLoginName());
            System.out.println("password:"+a.getPassword());
        }
    }
}
