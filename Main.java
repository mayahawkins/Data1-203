import java.util.Random;

interface BST {
	public int cardinality();
	public boolean member (int x);
	public BST empty();
	public boolean isEmptyHuh();
	public BST add(int x);
	public BST remove(int x);
	public BST union(BST t);
	public BST inter(BST t);
	public BST differ(BST t);
	public boolean equal(BST t);
	public boolean subset(BST t);
}

class BST_MT implements BST{
	BST_MT() { };

	public int cardinality(){
		return 0;
	}

	public boolean member (int x){
		return false;
	}

	public BST empty(){
		return new BST_MT();
	}

	public boolean isEmptyHuh(){
		return true;
	}

	public BST add(int x){
	return new BST_Node(new BST_MT(), x, new BST_MT());
	}

	public BST remove (int x) {
		return new BST_MT();
	}

	public BST union(BST t){
		return t;
	}

	public BST inter(BST t) {
		return new BST_MT();
	}

	public BST differ(BST t) {
		return new BST_MT();
	}
	public boolean equal(BST t){
		if (t.isEmptyHuh()){
			return true;
		}
		else {
			return false;
		}
	}

	public boolean subset(BST t){
		return true;
	}
}


class BST_Node implements BST {

	int here;
	BST left;
	BST right;

	BST_Node(BST left, int here, BST right) {
		this.left = left;
		this.here = here;
		this.right = right;
	}

	public int cardinality( /* BST_Node this */ ){
		return this.left.cardinality() + this.right.cardinality() + 1;
	}

	public boolean member (int x) {
		if (x == this.here) {
			return true; 
		}
		else if (x < this.here){
			return this.left.member(x);
		}
		else {return this.right.member(x);}
	}

	public BST empty(){
		return new BST_MT();
	}

	public boolean isEmptyHuh(){
		return false;
	}

	public BST add(int x){
		if (x == this.here) {
			return new BST_Node(this.left, this.here, this.right);
		}
		else if (x < this.here) {
		return new BST_Node(this.left.add(x), this.here, this.right);
		}
		else {return new BST_Node(this.left, this.here, this.right.add(x));
		}
	}

	public BST remove(int x){
		if (x == this.here) {
			return this.left.union(right);
		}
		else if (x < this.here) {
			return new BST_Node(this.left.remove(x), this.here, this.right);
		}
		else {return new BST_Node(this.left, this.here, this.right.remove(x));
		}
	}

	public BST union(BST t){
		return this.left.union(this.right).union(t).add(this.here);
	}

	public BST inter(BST t){
		if (t.member(this.here)) {
			return new BST_Node(this.left.inter(t), this.here, this.right.inter(t));
		}
		else {
			return this.left.union(this.right).inter(t);}
	}

	public BST differ(BST t){
		if(t.member(this.here)) {
			return this.left.union(this.right).differ(t);
		}
		else { 
			return new BST_Node(this.left.differ(t), this.here, this.right.differ(t));
		}
	}

	public boolean equal(BST t){
		if(t.member(this.here)) {
			return this.left.union(this.right).equal(t.remove(this.here));
		}
		else {
			return false;
		}
	}

	public boolean subset(BST t){
		return (t.member(this.here) && this.left.subset(t) && this.right.subset(t));
	}
}


class Main {

	//Work to allow ability to generate random numbers and BST
	static Random rand = new Random();
    public static int randomInt( int min, int max ) {
        return rand.nextInt((max - min) + 1) + min; }


	public static BST randBST(){
	return randBST(randomInt(0, 100));
	}

	public static BST randBST(int counter) {
		BST starter_bst = new BST_MT();
		while(counter != 0) {
			starter_bst.add(randomInt(0, 50));
			counter = counter - 1;
		}
		return starter_bst;
	}

	//First Property: if a number is added and removed to a BST, the cardinality
	//of the BST will wither the the same as when it first started or one less.
	public static void addRemEq(BST t, int x){
		BST save_bst = t;
		if (t.member(x)) {
			int difference = t.cardinality() - t.add(x).remove(x).cardinality();
			System.out.println(x +  " in input, output is " + difference + " less than input");
		}
		else if (t.add(x).remove(x).equal(save_bst)) {
			System.out.println("The input equals the output");
		}
		else {
			System.out.println("The input does not equal the output");
		}
	}

	//Function to allow multiple random calls of addRemEq function
	public static void addRemEqRepeat(){
		int count = randomInt(0, 100); 
		while(count != 0){
			addRemEq(randBST(), randomInt(0, 75));
			count = count - 1;
		}
	}

	//Second Property: if the inter of some BST u and t and the difference of u and t
	//are unionized,, they will be the same length as u.

	public static void inDiffCar(BST u, BST t) {
		BST save_u = u;
		if (u.inter(t).union(u.differ(t)).equal(save_u)) {
			System.out.println("inDiffCar Property calls true");
		}
		else {
			System.out.println("Property failed");
		}
	}

	//Function to allow multiple random calls of inDiffCar
	public static void inDiffCarRepeat(){
		int count = randomInt(0, 30);
		while(count != 0){
			inDiffCar(randBST(20), randBST(40));
			count = count - 1;
		}
	}

	//Third Property: the cardinality of the union of two BST are the same as
	//the sum of the cardinalities of each BST individually
	public static void unionAndCard(BST u, BST t){
		if(u.union(t).cardinality() == u.cardinality() + t.cardinality()) {
			System.out.println("unionAndCard Property holds");
		}
	}

	//Function to allow multiple random calls for unionAndCard
	public static void unionAndCardRepeat(){
		int count = randomInt(0, 30);
		while(count != 0) {
			unionAndCard(randBST(), randBST());
			count = count - 1; 
		}
	}

	//Fourth Property: If t is a subset of u, then the cardinality
	//of the inter between u and t will equal the cardinality of t 
	public static void subCard(BST u, BST t) {
		BST save_t = t;

		if (t.subset(u)){
			if(u.inter(t).cardinality() == save_t.cardinality()){
				System.out.println("subCard Property holds true");
			}
		}
		else {
			System.out.println("subCard Property is false");
		}
	}

	//Function allows for multiple random calls on subCard
	public static void subCardRepeat(){
		int count = randomInt(0, 30);
		while(count != 0){
			subCard(randBST(100), randBST(10));
			count = count - 1;
		}
	}

	//Not a property function.  Just used to either add or remove an element from a BST
	//Depending on if the element is in the BST or not
	public static void addOrRem(BST t, int x) {
		if (t.member(x)) {
			System.out.println(x + " is a member of t, if removed, member would call " + t.remove(x).member(x));
		}
		else{ 
			System.out.println(x + " is not a member of t, if added, member would call " + t.add(x).member(x));
		}
	}
	

	public static void main ( String [] args ) {
		//BSTs for tests
		BST b_mt = new BST_MT();
		BST b_1 = new BST_Node(b_mt, 1, b_mt);
		BST b_5 = new BST_Node(b_1, 5, b_mt);
		BST b_4 = new BST_Node(b_mt, 4, b_mt);
		BST b_3 = new BST_Node(b_mt, 3, b_4);

		System.out.println("   ");

		//isEmptyHuh and cardinality testers
		System.out.println(b_mt.isEmptyHuh() + " should be " + true);
		System.out.println(b_mt.cardinality() + " should be " + 0);
		System.out.println(b_5.isEmptyHuh() + " should be " + false);
		System.out.println(b_5.cardinality() + " should be " + 2);

		System.out.println("   ");

		//addRemEq testers
		addRemEq(b_3, 6);

		System.out.println("   ");

		addRemEq(b_5, 6);

		System.out.println("   ");

		addRemEq(b_5, 5);

		System.out.println("   ");

		addRemEqRepeat();

		System.out.println("   ");


		//inDiffCar testers
		inDiffCar(b_5, b_1);
		inDiffCarRepeat();

		System.out.println("   ");

		//unionAndCard testers
		unionAndCard(b_mt, b_mt);
		unionAndCard(b_mt, b_3);
		unionAndCardRepeat();

		System.out.println("   ");

		//subCard testers
		subCard(b_5, b_1);
		subCard(randBST(100), randBST(5));
		subCardRepeat();

		System.out.println("   ");	

		//add and remove only with member testers
		addOrRem(b_5, 5);
		addOrRem(b_5, 2);
		addOrRem(randBST(30), randomInt(0, 40));
		System.out.println("   ");

		//Other random tests
		System.out.println(b_5.add( 2 ).member(2) + " should be " + true);
		System.out.println(b_4.union(b_5).cardinality() + " should be " + 3);
		System.out.println(b_4.union(b_mt).cardinality() + " should be " + 1);
		System.out.println(b_5.inter(b_5).cardinality() + " should be " + 2); 
		System.out.println(b_mt.subset(b_mt) + " should be " + true);
		System.out.println(b_1.subset(b_5) + " should be " + true);
		System.out.println(b_5.subset(b_1) +  " should be " + false);
		System.out.println(b_5.subset(b_4.union(b_5)) + " should be " + true);



	}	
}