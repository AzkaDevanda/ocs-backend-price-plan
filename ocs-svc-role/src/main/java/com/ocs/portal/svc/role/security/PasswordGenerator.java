//package com.sts.sinorita.svc.role.security;
//
//import java.security.SecureRandom;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//public class PasswordGenerator {
//
//    private static final char[] ALLOWED_SPECIAL_CHARACTERS = new char[] {
//            '!', '@', '#', '$', '%', '^', '&', '*', '_', '+',
//            '-', '{', '}', '<', '>', '.', '?' };
//
//    private static final int letterRange = 26;
//
//    private static final int numberRange = 10;
//
//    private static final int spCharacterRange = ALLOWED_SPECIAL_CHARACTERS.length;
//
//    private static final SecureRandom random = new SecureRandom();
//
//    private int passwordLength;
//
//    private int pwdComposition;
//
//    public PasswordGenerator() {}
//
//    public PasswordGenerator(int passwordLength, int pwdComposition) {
//        if (pwdComposition > (CharacterType.values()).length + 1)
//            pwdComposition = (CharacterType.values()).length + 1;
//        if (pwdComposition < 0)
//            pwdComposition = 1;
//        this.passwordLength = passwordLength;
//        this.pwdComposition = pwdComposition;
//    }
//
//    public enum SecurityRule {
//        LOWEST(-1L, 5, "3", "1"),
//        LOWER(0L, 8, "3", "1"),
//        LOW(1L, 8, "23", "2"),
//        MODERATE(2L, 8, "123", "3"),
//        HIGH(3L, 8, "234", "4"),
//        HIGHER(4L, 8, "1234", "5");
//
//        private final long levelId;
//
//        private final int pwdLength;
//
//        private final String pwdComplexity;
//
//        private final String pwdComposition;
//
//        SecurityRule(long levelId, int pwdLength, String pwdComplexity, String pwdComposition) {
//            this.levelId = levelId;
//            this.pwdLength = pwdLength;
//            this.pwdComplexity = pwdComplexity;
//            this.pwdComposition = pwdComposition;
//        }
//
//        public long getLevelId() {
//            return this.levelId;
//        }
//
//        public int getPwdLength() {
//            return this.pwdLength;
//        }
//
//        public String getPwdComplexity() {
//            return this.pwdComplexity;
//        }
//
//        public String getPwdComposition() {
//            return this.pwdComposition;
//        }
//    }
//
//    public static String generateRandomPassword(Integer pwdLength, String pwdComplexity, String pwdComposition) {
//        char[] password = new char[pwdLength.intValue()];
//        List<Integer> pwCharsIndex = new ArrayList<>();
//        for (int i = 0; i < password.length; i++)
//            pwCharsIndex.add(Integer.valueOf(i));
//        List<CharacterType> takeTypes = new ArrayList<>();
//        if (pwdComplexity.indexOf("1") > -1)
//            takeTypes.add(CharacterType.UPPERCASE);
//        if (pwdComplexity.indexOf("2") > -1)
//            takeTypes.add(CharacterType.LOWERCASE);
//        if (pwdComplexity.indexOf("3") > -1)
//            takeTypes.add(CharacterType.NUMBER);
//        if (pwdComplexity.indexOf("4") > -1)
//            takeTypes.add(CharacterType.SPECIAL_CHARACTER);
//        int composition = Integer.parseInt(pwdComposition);
//        if (composition > takeTypes.size())
//            composition = takeTypes.size();
//        int x = random.nextInt(takeTypes.size());
//        List<Integer> randomIndex = null;
//        if (composition != 1)
//            randomIndex = randomPwdCategory(composition, takeTypes.size(), pwCharsIndex.size());
//        while (!pwCharsIndex.isEmpty()) {
//            int pwIndex = ((Integer)pwCharsIndex.remove(random.nextInt(pwCharsIndex.size()))).intValue();
//            if (composition != 1)
//                x = ((Integer)randomIndex.get(pwIndex)).intValue();
//            Character c = generateCharacter(takeTypes.get(x));
//            password[pwIndex] = c.charValue();
//        }
//        return String.valueOf(password);
//    }
//
//    public String generateRandomPassword() {
//        int i, sonType, j, splitPosition, k;
//        StringBuilder password = new StringBuilder();
//        List<CharacterType> types = new ArrayList<>();
//        switch (this.pwdComposition) {
//            case 0:
//                for (i = 0; i < this.passwordLength; i++) {
//                    Character c = generateCharacter(CharacterType.NUMBER);
//                    password.append(c);
//                }
//                break;
//            case 1:
//                sonType = random.nextInt(3);
//                switch (sonType) {
//                    case 0:
//                        for (j = 0; j < this.passwordLength; j++) {
//                            Character c = generateCharacter(CharacterType.NUMBER);
//                            password.append(c);
//                        }
//                        break;
//                    case 1:
//                        for (j = 0; j < this.passwordLength; j++) {
//                            Character c = generateCharacter(CharacterType.LOWERCASE);
//                            password.append(c);
//                        }
//                        break;
//                    case 2:
//                        splitPosition = this.passwordLength / 2;
//                        for (k = 0; k < this.passwordLength; k++) {
//                            Character c;
//                            if (k < splitPosition) {
//                                c = generateCharacter(CharacterType.LOWERCASE);
//                            } else {
//                                c = generateCharacter(CharacterType.NUMBER);
//                            }
//                            password.append(c);
//                        }
//                        break;
//                }
//                break;
//            case 2:
//                types.add(CharacterType.LOWERCASE);
//                types.add(CharacterType.NUMBER);
//                password.append(generateRandomPassword(types));
//                break;
//            case 3:
//                types.add(CharacterType.LOWERCASE);
//                types.add(CharacterType.NUMBER);
//                types.add(CharacterType.UPPERCASE);
//                password.append(generateRandomPassword(types));
//                break;
//            case 4:
//                types.add(CharacterType.LOWERCASE);
//                types.add(CharacterType.NUMBER);
//                types.add(CharacterType.SPECIAL_CHARACTER);
//                password.append(generateRandomPassword(types));
//                break;
//            case 5:
//                types = new ArrayList<>(Arrays.asList(CharacterType.values()));
//                password.append(generateRandomPassword(types));
//                break;
//        }
//        return password.toString();
//    }
//
//    public String generateRandomPassword(List<CharacterType> takeTypes) {
//        char[] password = new char[this.passwordLength];
//        List<Integer> pwCharsIndex = new ArrayList<>();
//        for (int i = 0; i < password.length; i++)
//            pwCharsIndex.add(Integer.valueOf(i));
//        List<CharacterType> fixedTypes = new ArrayList<>(takeTypes);
//        int typeCount = 0;
//        while (!pwCharsIndex.isEmpty()) {
//            Character c;
//            int pwIndex = ((Integer)pwCharsIndex.remove(random.nextInt(pwCharsIndex.size()))).intValue();
//            if (typeCount < fixedTypes.size()) {
//                c = generateCharacter(takeTypes.remove(random.nextInt(takeTypes.size())));
//                typeCount++;
//            } else {
//                c = generateCharacter(fixedTypes.get(random.nextInt(fixedTypes.size())));
//            }
//            password[pwIndex] = c.charValue();
//        }
//        return String.valueOf(password);
//    }
//
//    private static Character generateCharacter(CharacterType type) {
//        int rand;
//        Character c = null;
//        switch (type) {
//            case LOWERCASE:
//                rand = random.nextInt(26);
//                rand += 97;
//                c = Character.valueOf((char)rand);
//                break;
//            case UPPERCASE:
//                rand = random.nextInt(26);
//                rand += 65;
//                c = Character.valueOf((char)rand);
//                break;
//            case NUMBER:
//                rand = random.nextInt(10);
//                rand += 48;
//                c = Character.valueOf((char)rand);
//                break;
//            case SPECIAL_CHARACTER:
//                rand = random.nextInt(spCharacterRange);
//                c = Character.valueOf(ALLOWED_SPECIAL_CHARACTERS[rand]);
//                break;
//        }
//        return c;
//    }
//
//    private static List<Integer> randomPwdCategory(int composition, int rangeSize, int charSize) {
//        List<Integer> list = new ArrayList<>(charSize);
//        List<Integer> rangeList = new ArrayList<>(composition);
//        while (rangeList.size() < composition) {
//            int x = random.nextInt(rangeSize);
//            if (!rangeList.contains(Integer.valueOf(x)))
//                rangeList.add(Integer.valueOf(x));
//        }
//        list.addAll(rangeList);
//        for (int i = 0; i < charSize; i++) {
//            if (list.size() < charSize)
//                list.add(rangeList.get(random.nextInt(rangeList.size())));
//        }
//        Collections.shuffle(list);
//        return list;
//    }
//
//    enum CharacterType {
//        LOWERCASE, UPPERCASE, NUMBER, SPECIAL_CHARACTER;
//    }
//}
