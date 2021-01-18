#include <iostream>

class Custom
{
private:
	int a = 1;
public:
	int getA()
	{
		return a;
	}
};

int main(int argc, char const *argv[])
{
	char str[20];
	int number = 0;
	printf("Start!\n");

	FILE *file = fopen("input.txt", "r");
	fscanf(file, "%s%d", str, &number);

	printf("str = %s\nnumber = %d\n", str, number);

	Custom simple;
	printf("number a = %d\n", simple.getA());
	fclose(file);

	return 0;
}