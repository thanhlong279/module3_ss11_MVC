<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Deleting customer</title>
</head>
<body>
<h1>Delete product</h1>
<p>
  <a href="/list">Back to product list</a>
</p>
<f
<h3>Are you sure?</h3>
<fieldset>
  <legend>Product information</legend>
  <table>
    <tr>
      <td>Name: </td>
      <td>${requestScope["product"].name}</td>
    </tr>
    <tr>
      <td>Email: </td>
      <td>${requestScope["product"].price}</td>
    </tr>
    <tr>
      <td>Description: </td>
      <td>${requestScope["product"].description}</td>
    </tr>
    <tr>
      <td>Producer: </td>
      <td>${requestScope["product"].producer}</td>
    </tr>
    <tr>
      <td><input type="submit" value="Delete product"></td>
      <td><a href="/list">Back to customer list</a></td>
    </tr>
  </table>
</fieldset>
</body>
</html>