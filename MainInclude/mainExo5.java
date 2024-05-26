
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int result = countDigits(n);
        System.out.println("Le nombre de chiffres de " + n + " est " + result);
        scanner.close();
    }
}
