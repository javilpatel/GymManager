package fitness;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MemberListAddTest {

    @Test
    public void checkDuplicateMember() {
        MemberList memberList = new MemberList();

        Profile profile1 = new Profile("Fred", "Holland", new Date("3/4/1998"));
        Member member1 = new Member(profile1, new Date("7/2/2026"), Location.EDISON);

        // Valid Case: Attempt to add the member for the first time should succeed
        assertTrue("First attempt to add member should succeed", memberList.add(member1));

        // Invalid Case: Attempt to add the same member a second time should fail
        assertFalse("Second attempt to add the same member should fail due to duplication", memberList.add(member1));
    }


    @Test
    public void testCaseSensitivity() {
        MemberList memberList = new MemberList();

        Profile profileValid = new Profile("John", "Doe", new Date("12/11/1978"));
        Member memberValid = new Member(profileValid, new Date("6/29/2030"), Location.SOMERVILLE);

        // Attempt to add a similar member with a slight variation in name case
        Profile profileCaseSensitive = new Profile("john", "doe", new Date("12/11/1978"));
        Member memberCaseSensitive = new Member(profileCaseSensitive, new Date("6/29/2030"), Location.SOMERVILLE);

        // Valid Case: Adding the first member should succeed
        assertTrue(memberList.add(memberValid));
        // Invalid Case: Adding the second member with case variation should fail if names are treated case-insensitively
        assertFalse(memberList.add(memberCaseSensitive));
    }

    @Test
    public void checkInvalidDOBInsertion() {
        MemberList memberList = new MemberList();

        // Invalid DOB
        Profile profileInvalidDOB = new Profile("Jack", "Johnson", new Date("12/14/2027"));
        Member memberInvalidDOB = new Member(profileInvalidDOB, new Date("6/12/2024"), Location.BRIDGEWATER);

        // Invalid Case: Adding a member with an invalid DOB should fail
        assertTrue(memberList.add(memberInvalidDOB));
    }

    @Test
    public void addOne() {
        MemberList memberList = new MemberList();
        Profile profile2 = new Profile("Eric", "Chan", new Date("8/17/2003"));
        Member member2 = new Member(profile2, new Date("8/22/2028"), Location.FRANKLIN);

        // Valid Case: This checks that adding a new member to the list should succeed
        assertTrue(memberList.add(member2));
    }

}
