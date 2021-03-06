package com.flannery.lib.hashmap;

public class TableSizeForDemo {


    static final int MAXIMUM_CAPACITY = 1 << 30;

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println("\t\t" + i + "-->" + tableSizeFor(i));
        }
    }

    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }
    // 结果 2的数倍
    //		0-->1
    //		1-->1
    //		2-->2
    //		3-->4
    //		4-->4
    //		5-->8
    //		6-->8
    //		7-->8
    //		8-->8
    //		9-->16
    //		10-->16
    //		11-->16
    //		12-->16
    //		13-->16
    //		14-->16
    //		15-->16
    //		16-->16
    //		17-->32
    //		18-->32
    //		19-->32
    //		20-->32
    //		21-->32
    //		22-->32
    //		23-->32
    //		24-->32
    //		25-->32
    //		26-->32
    //		27-->32
    //		28-->32
    //		29-->32
    //		30-->32
    //		31-->32
    //		32-->32
    //		33-->64
    //		34-->64
    //		35-->64
    //		36-->64
    //		37-->64
    //		38-->64
    //		39-->64
    //		40-->64
    //		41-->64
    //		42-->64
    //		43-->64
    //		44-->64
    //		45-->64
    //		46-->64
    //		47-->64
    //		48-->64
    //		49-->64
    //		50-->64
    //		51-->64
    //		52-->64
    //		53-->64
    //		54-->64
    //		55-->64
    //		56-->64
    //		57-->64
    //		58-->64
    //		59-->64
    //		60-->64
    //		61-->64
    //		62-->64
    //		63-->64
    //		64-->64
    //		65-->128
    //		66-->128
    //		67-->128
    //		68-->128
    //		69-->128
    //		70-->128
    //		71-->128
    //		72-->128
    //		73-->128
    //		74-->128
    //		75-->128
    //		76-->128
    //		77-->128
    //		78-->128
    //		79-->128
    //		80-->128
    //		81-->128
    //		82-->128
    //		83-->128
    //		84-->128
    //		85-->128
    //		86-->128
    //		87-->128
    //		88-->128
    //		89-->128
    //		90-->128
    //		91-->128
    //		92-->128
    //		93-->128
    //		94-->128
    //		95-->128
    //		96-->128
    //		97-->128
    //		98-->128
    //		99-->128
}
