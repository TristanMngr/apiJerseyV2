<%--
  Created by IntelliJ IDEA.
  User: tristanmenager
  Date: 10/11/2018
  Time: 20:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Alarm</title>
    <link rel="stylesheet" href="../../css/styles.css">
</head>
<jsp:include page="/jsp/shared/header.jsp" flush="true"/>
<h3 class="display-4 text-center">Alarms</h3>
<hr/>
<div class="container">
    <div class="col-12">
        <div class="row">
            <div class="col-md-4">
                <div class="row">
                    <h3 class="text-center">Create Alarm</h3>
                </div>
                <hr/>
                <div class="form-group row">
                    <label for="alarmName" class="col-md-3 col-form-label">Name</label>
                    <div class="col-md-9">
                        <input type="text" class="form-control" id="alarmName" name="alarmName">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="alarmCategory" class="col-md-3 col-form-label">Categoría</label>
                    <div class="col-md-9">
                        <select class="form-control" id="alarmCategory" name="alarmCategory">
                            <option value="">All</option>
                        </select>
                    </div>
                </div>
                <div class="row">
                    <div class="col-12 text-center">
                        <button type="button" id="btnCreateAlarm" class="btn btn-info">Create Alarm</button>
                    </div>
                </div>
                <br>
                <div class="row">
                    <div id="message">
                    </div>
                </div>
            </div>

            <div class="col-md-8 table-responsive">
                <div class="row">
                    <h3 class="text-center">List Alarm</h3>
                </div>
                <hr>
                <table id="alarmList" class="table table-hover table-sm">
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Category</th>
                        <th>Events</th>
                        <th>Remove</th>
                    </tr>
                    </thead>
                    <tbody>

                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/jsp/shared/footer.jsp" flush="true"/>
<script src="/js/alarm/index.js"></script>
</html>
