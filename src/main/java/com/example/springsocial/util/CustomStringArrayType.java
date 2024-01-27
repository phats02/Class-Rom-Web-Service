//package com.example.springsocial.util;
//
//import org.hibernate.usertype.UserType;
//
//public abstract class CustomStringArrayType implements UserType<int[]> {
//    @Override
//    public int sqlType() {
//        return Types.ARRAY;
//    }
//
//    @Override
//    public Class returnedClass() {
//        return String[].class;
//    }
//
//    @Override
//    public String[] nullSafeGet(ResultSet rs, int position, SharedSessionContractImplementor session,
//                                Object owner) throws SQLException {
//        Array array = rs.getArray(position);
//        return array != null ? (String[]) array.getArray() : null;
//    }
//
//    @Override
//    public void nullSafeSet(PreparedStatement st, String[] value, int index,
//                            SharedSessionContractImplementor session) throws SQLException {
//        if (st != null) {
//            if (value != null) {
//                Array array = session.getJdbcConnectionAccess().obtainConnection()
//                        .createArrayOf("text", value);
//                st.setArray(index, array);
//            } else {
//                st.setNull(index, Types.ARRAY);
//            }
//        }
//    }
//    //implement equals, hashCode, and other methods
//}
