interface BST {
	public int cardinality();
	public boolean member (int x);
	public BST_MT empty();
	public boolean isEmpyHuh(BST);
}

class BST_MT implements BST{
	BST_MT() { };

	public int cardinality(){
		return 0;
	}
	public boolean member (int x){
		return false;
	}
	public BST_MT empty(){
		return new BST_MT();
	}
	public boolean isEmpyHuh(BST_MT){
		return false;
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
	public BST_MT empty(){
		return new BST_MT();
	}

	public boolean isEmpyHuh(BST_Node){
		return true;
	}


}

class Main {
public static void main ( String [] args ) {
	System.out.println("Hey!");

}
}