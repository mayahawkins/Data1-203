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
	
	if (this.cardinality() < t.cardinality()){
		return false;
	}
	else if (t.member(this.here) && this.left.union(this.right).subset(t.remove(this.here))) {
		return true;
	}
	else {
		return this.left.union(this.right).subset(t);
		}
	}
}


class Main {

	public static void addRemEq(BST t, int x){
		BST save_bst = t;
		if (t.member(x)) {
			System.out.println(x + " is a member of t choose a different number");
		}
		else if (t.add(x).remove(x).equal(save_bst)) {
			System.out.println("The input equals the output");
		}
		else {
			System.out.println("The input does not equal the output");
		}
	}

	public static void inDiffCar(BST u, BST t) {
		BST save_u = u;
		if (u.inter(t).union(u.differ(t)).equal(save_u)) {
			System.out.println("Property calls true");
		}
		else {
			System.out.println("Property failed");
		}
	}

	public static void addOrRem(BST t, int x) {
		if (t.member(x)) {
			System.out.println(x + " is a member of t, if removed, member would call " + t.remove(x).member(x));
		}
		else{ 
			System.out.println(x + " is not a member of t, if added, member would call " + t.add(x).member(x));
		}
	}

	public static void main ( String [] args ) {
		//BSTs
		BST b_mt = new BST_MT();
		BST b_1 = new BST_Node(b_mt, 1, b_mt);
		BST b_5 = new BST_Node(b_1, 5, b_mt);
		BST b_4 = new BST_Node(b_mt, 4, b_mt);
		BST b_3 = new BST_Node(b_mt, 3, b_mt);
		BST b_happy = new BST_Node(b_3, 7, b_mt);


		//isEmptyHuh and cardinality testers
		System.out.println(b_mt.isEmptyHuh() + " should be " + true);
		System.out.println(b_mt.cardinality() + " should be " + 0);
		System.out.println(b_5.isEmptyHuh() + " should be " + false);
		System.out.println(b_5.cardinality() + " should be " + 2);

		
		//addRemEq testers
		addRemEq(b_3, 6);
		addRemEq(b_5, 6);
		addRemEq(b_5, 5);


		//inDiffCar testers
		inDiffCar(b_5, b_1);


		//add and remove only with member testers
		addOrRem(b_5, 5);
		addOrRem(b_5, 2);


		System.out.println(b_5.add( 2 ).member(2) + " should be " + true);

		System.out.println(b_4.union(b_5).cardinality() + " should be " + 3);
		System.out.println(b_4.union(b_mt).cardinality() + " should be " + 1);
		System.out.println(b_5.inter(b_5).cardinality() + " should be " + 2); 
		System.out.println(b_mt.subset(b_mt) + " should be " + true);
		System.out.println(b_5.subset(b_1) + " should be " + true);
		System.out.println(b_1.subset(b_5) +  " should be " + false);
		System.out.println(b_4.union(b_5).subset(b_5) + " should be " + true);

	}	
}