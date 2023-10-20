# For MemberAddress table didnt provide UPDATE function to it because it only have 2 primary key col

# How to access DBSet
- to using DBSet please invoke DBTable class at <b>Data Access package</b>
- DbSet is only allow for the Model that extends to DBModel

## DbSet
- DbSet provide function CRUD function
- DBTable.Staff.getData(new StaffMapper()) will return ArrayList<Staff>

### Method provide
- public ArrayList<T> getData(RowMapper<T> mapper) throws SQLException
	- Get All Data
- public ArrayList<T> getData(RowMapper<T> mapper, int rowID) throws SQLException
	- Get Data With ID
- public ArrayList<T> getData(RowMapper<T> mapper, ArrayList<Object> condition, String sqlQuery) throws SQLException
	- Get Data With Query
	- Example : SELECT * FROM Staff WHERE staff_id = ?
	- When writing sql query at the end doesnt need semicolon ';'
- public boolean Add(RowMapper<T> mapper, T t) throws SQLException
- public boolean Update(RowMapper<T> mapper, T t) throws SQLException
- public boolean Delete(RowMapper<T> mapper, T t) throws SQLException

# What is Mapper
- Mapper is use to let the program know which table and function are using so please dont pass wrong
- Example for Mapper : StaffMapper, MemberMapper (tableName + Mapper)