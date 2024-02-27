package fitness;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MemberListRemoveTest {

    //Invalid Case: member no added to list prior to remove
    @Test
    public void checkEmptyRemoval() {
        Date expiration1 = new Date("6/12/2024");
        Date dob1 = new Date("12/14/2003");
        Profile user1 = new Profile("Jack", "Johnson", dob1);
        Member member1 = new Member(user1, expiration1, Location.BRIDGEWATER);

        MemberList list = new MemberList();

        assertFalse(list.remove(member1));
    }

    //Valid Case: member was removed from list
    @Test
    public void checkRemoval() {
        Date expiration1 = new Date("6/12/2024");
        Date dob1 = new Date("12/14/2003");
        Profile user1 = new Profile("Jack", "Johnson", dob1);
        Member member1 = new Member(user1, expiration1, Location.BRIDGEWATER);

        MemberList list = new MemberList();
        list.add(member1);

        assertTrue(list.remove(member1));
    }

}
