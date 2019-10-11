import java.util.Scanner;

public class triangle {

    public static void main(String [] args) {

	int x, y, z;
		Scanner reader = new Scanner(System.in);

	System.out.print("Enter three side lengths: ");
	x = reader.nextInt();
	y = reader.nextInt();
	z = reader.nextInt();

	if (x + y > z && y + z > x && z + x > y)
	{
		if (x * x + y * y == z * z || y * y + z * z == x * x || z * z + x * x == y * y)
			System.out.println("It is a right triangle!");
		else
			System.out.println("It is a triangle but not a right one!");
	}
	else
		System.out.println("It is not a trangle!");

    }
	// TEST


}
