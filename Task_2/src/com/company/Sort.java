package com.company;

import java.util.ArrayList;
public class Sort {
    static class Node {
        int data;
        Node previous; //предыдущий узел
        Node next; //следующий узел
    }

    public static Node append(Node start, int data) {
        Node value = new Node();
        value.data = data;
        value.next = start;
        if (start != null)
            (start).previous = value;
        start = value;
        return start;
    }

    static ArrayList<Integer> printList(Node start) {
        ArrayList<Integer> list = new ArrayList<>();
        Node temp = start;
        System.out.println();
        while (temp != null) {
            list.add(temp.data);
            temp = temp.next;
        }
        return list;
    }

    // Пузырьковая сортировка
    static Node bubbleSort(Node head) {

        // Проверка на пустоту
        if (head == null || head.next == null) {
            return head;
        }

        boolean swapped;
        do {
            swapped = false;
            Node prev = null;
            Node curr = head;
            Node next = head.next;
            while (next != null) {
                if (curr.data > next.data) {
                    // меняем узлы
                    if (prev != null) {
                        prev.next = next;
                    } else {
                        head = next;
                    }
                    curr.next = next.next;
                    next.next = curr;

                    prev = next;
                    next = curr.next;
                    swapped = true;
                } else {

                    prev = curr;
                    curr = next;
                    next = next.next;
                }
            }
        } while (swapped);

        return head;
    }
}
