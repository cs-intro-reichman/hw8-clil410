/** Represents a social network. The network has users, who follow other uesrs.
 *  Each user is an instance of the User class. */
public class Network {

    // Fields
    private User[] users;  // the users in this network (an array of User objects)
    private int userCount; // actual number of users in this network

    /** Creates a network with a given maximum number of users. */
    public Network(int maxUserCount) {
        this.users = new User[maxUserCount];
        this.userCount = 0;
    }

    /** Creates a network  with some users. The only purpose of this constructor is 
     *  to allow testing the toString and getUser methods, before implementing other methods. */
    public Network(int maxUserCount, boolean gettingStarted) {
        this(maxUserCount);
        users[0] = new User("Foo");
        users[1] = new User("Bar");
        users[2] = new User("Baz");
        userCount = 3;
    }

    public int getUserCount() {
        return this.userCount;
    }

    /** Finds in this network, and returns, the user that has the given name.
     *  If there is no such user, returns null.
     *  Notice that the method receives a String, and returns a User object. */
    public User getUser(String name) {
        String properName = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        for (int i = 0; i < userCount; i++) {
            if (users[i].getName().equals(properName)) {
                return users[i];
            }
        }
        return null;
    }

    /** Adds a new user with the given name to this network.
    *  If ths network is full, does nothing and returns false;
    *  If the given name is already a user in this network, does nothing and returns false;
    *  Otherwise, creates a new user with the given name, adds the user to this network, and returns true. */
    public boolean addUser(String name) {
        if (userCount >= users.length) {
            System.out.println("network is full. cant add " + name);
            return false;
        }
        for (int i = 0; i < userCount; i++) {
            if (users[i].getName().equals(name)) {
                System.out.println(name + " is allready in the network.");
                return false;
            }
        }
        users[userCount] = new User(name);
        userCount++;
        return true;
    }

    /** Makes the user with name1 follow the user with name2. If successful, returns true.
     *  If any of the two names is not a user in this network,
     *  or if the "follows" addition failed for some reason, returns false. */
    public boolean addFollowee(String name1, String name2) {
        boolean name1IsUser = false;
        boolean name2IsUser = false;
        int indexName1 = -1;
        String properName1 = name1.substring(0, 1).toUpperCase() + name1.substring(1).toLowerCase();
        String properName2 = name2.substring(0, 1).toUpperCase() + name2.substring(1).toLowerCase();
        for (int i = 0; i < userCount; i++) {
            if (users[i].getName().equals(properName1)) {
                name1IsUser = true;
                indexName1 = i;
                //System.out.println("found name 1 " + users[i].getName()); 
            }
            if (users[i].getName().equals(properName2)) {
                name2IsUser = true;
                //System.out.println("found name 2 " + users[i].getName()); 
            }
        }
        if ((name1IsUser == false) || (name2IsUser == false)) {
            System.out.println ("one of the users is not in the network");
            return false;
        }
        return (users[indexName1].addFollowee(properName2));
    }
    
    /** For the user with the given name, recommends another user to follow. The recommended user is
     *  the user that has the maximal mutual number of followees as the user with the given name. */
    public String recommendWhoToFollow(String name) {
        User mostRecommendedUserToFollow = null;
        int maximalMutual = 0;
        User thisUser = null;
        int thisPlace = 0;
        for (int i = 0; i < userCount; i++) {
            if (users[i].getName().equals(name)) {
                thisUser = users[i];
                thisPlace = i;
            }
        }
        for (int j = 0; j < userCount; j++) {
            if (j == thisPlace) continue;
            int MutualCount = thisUser.countMutual(users[j]);
            if (MutualCount > maximalMutual) {
                maximalMutual = MutualCount;
                mostRecommendedUserToFollow = users[j];
            }
        }
        return mostRecommendedUserToFollow.getName();
    }

    /** Computes and returns the name of the most popular user in this network: 
     *  The user who appears the most in the follow lists of all the users. */
    public String mostPopularUser() {
        String mostPopular = users[0].getName();
        int numOfFollows = 0;
        for (int i = 0; i < userCount; i++) {
            String name = users[i].getName();
            int count = followeeCount(name);
            if (count > numOfFollows) {
                numOfFollows = count;
                mostPopular = name;
            }
        }
        return mostPopular;
    }

    /** Returns the number of times that the given name appears in the follows lists of all
     *  the users in this network. Note: A name can appear 0 or 1 times in each list. */
    private int followeeCount(String name) {
        String properName = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        int followsSum = 0;
        for (int i = 0; i < userCount; i++) {
            if (users[i].getName().equals(properName)) continue;
            int isFollow = (users[i].follows(properName)) ? 1 : 0;
            followsSum += isFollow;   
        }
        return followsSum;
    }

    // Returns a textual description of all the users in this network, and who they follow.
    public String toString() { 
        String ans = "Network: \n";
        for (int i = 0; i < userCount; i++) {
            ans += (users[i].getName() + " -> ");
            for (int j = 0; j < users[i].getfCount(); j++) {
                ans = ans + users[i].getfFollows()[j] + " ";
            }
            ans += "\n";
        }
        return (ans);
    }
}

