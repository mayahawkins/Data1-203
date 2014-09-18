interface BST {
	public int cardinality();
	public boolean member (int x);
	public BST empty();
	public boolean isEmpyHuh(BST);
	public BST add(int x);

}

class BST_MT implements BST{
	BST_MT() { };

	public int cardinality(){
		return 0;add
	}
	public boolean member (int x){
		return false;
	}
	public BST empty(){
		return new BST_MT();
	}
	public boolean isEmpyHuh(BST_MT){
		return false;
	}


	public BST add(int x){
	return new BST(BST_MT(), int x, BST_MT());
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

	public boolean isEmpyHuh(BST_Node){
		return true;
	}
	public BST add(int x){
		if (x == this.here) {
			return new BST(this.left, this.here, this.right);
		}
		else if (x < this.here) {
		return new BST_Node(this.left.add(x), this.here, this.right)
		}
		else {return new BST_Node(this.left, this.here this.right.add(x))}
	}
}

class Main {
public static void main ( String [] args ) {
	System.out.println("Hey!");

}
}