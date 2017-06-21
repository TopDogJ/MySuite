var selectedSecurityCode;

$(document).ready(function(){
    $('#userSecurityTableHeader').hide();
    var securityQuoteWss;
	var fetchQuoteInterval;
	var securityCode;
	var defaultDate = "2016-01-01";
	var userSecurityTable;

	$("#searchButton").click(function(){
	    selectedSecurityCode = $("#searchInput").val()
    	clearInterval(fetchQuoteInterval);
    	initialiseDefaultView();
    	fetchQuoteRealtime();
    	fetchQuoteInterval = setInterval("fetchQuoteRealtime()",60000);
    });

    $("#logonButton").click(function(){
        logon($("#usernameInput").val(),$("#passwordInput").val(), $("#memberMeCheckBox").get(0).checked);
    });

    $("#registerButton").click(function(){
        register($("#usernameInput").val(),$("#passwordInput").val());
    });

    $("#logonModel").on('hidden.bs.modal', function () {
        $("#usernameInput").val("");
        $("#passwordInput").val("");
        $("#memberMeCheckBox").get(0).checked = false;
    });

    $("#addSecurityButton").click(function(){
        userAddSecurity();
    });
});

function userAddSecurity(){
    $.post(
        "http://192.168.3.14:9090/mytrade-application-1.0/user/security/add",
        {
            securityCode: securityCode
        },
        function(data, status){
            if(data.length > 0){
                for(var i = 0; i< data.length; i++){
                    userSecurityTable.html("");
                    userSecurityTable.html("<tr>"
                        + "<td width='35%' class='text-left'><h6>" + data[i].name + "</h6></td>"
                        + "<td width='35%' class='text-left'><h6>" + data[i].code + "</h6></td>"
                        + "<td width='30%' class='text-left' id='" + data[i].code + "CurrentAt" + "'><h6>---</h6></td>"
                    + "</tr>");
                }
                userSecurityTable.dataTable({
                    "dom": 'tpr',
                    "bLengthChange": false,
                    "bAutoWidth": false,
                    "bInfo": false,
                    "bFilter":true,
                    "oLanguage": {
                        "oPaginate": {
                            "sFirst": "首页",
                            "sPrevious": "<<",
                            "sNext": ">>",
                            "sLast": "尾页"
                        }
                    }
                });
            }
        }
    );
}

function logon(username, password, rememberMe){
    $.post(
        "http://192.168.3.14:9090/mytrade-application-1.0/user/logon",
        {
            username: username,
            password: password,
            rememberMe: rememberMe
        },
        function(data, status){
            if(status == "success"){
                $("#logonModel").modal('hide');
                $("#commentButton").prop('disabled', false);
                $("#alertButton").prop('disabled', false);
                $("#addSecurityButton").prop('disabled', false);
                $("#configButton").prop('disabled', false);
                $("#boardCastButton").prop('disabled', false)
                securityQuoteWss = new SockJS("http://192.168.3.14:9090/mytrade-application-1.0/security/quote");
                initialiseUserSelectSecurity(data.selectedSecurity);
            }else{
                $("#logonModel").modal('hide');
                alert("Logon Failed.");
            }
        }
    );
}

function initialiseUserSelectSecurity(selectedSecurities){
    if(selectedSecurities.length > 0){
        userSecurityTable = $('#userSecurityTable').DataTable({
            "data": selectedSecurities,
            "columns": [
                { "data": "name" },
                { "data": "code" },
                { "data": "currentQuote.currentAt"}
            ],
            "dom": 'tpr',
            "bLengthChange": false,
            "bAutoWidth": false,
            "bInfo": false,
            "bFilter":true,
            "oLanguage": {
                "oPaginate": {
                    "sFirst": "首页",
                    "sPrevious": "<<",
                    "sNext": ">>",
                    "sLast": "尾页"
                }
            }
        });

        userSecurityTable.on('click', 'tr', function(event, dataTable, type, indexes){
            var selectedData = userSecurityTable.row( this ).data();
            var fetchQuoteInterval;
            selectedSecurityCode = selectedData.code;
            clearInterval(fetchQuoteInterval);
            initialiseDefaultView();
            fetchQuoteRealtime();
            fetchQuoteInterval = setInterval("fetchQuoteRealtime()",60000);
        });

        return userSecurityTable;
    }
}

function register(username, password){
    $.post(
        "http://192.168.3.14:9090/mytrade-application-1.0/user/register",
        {
            username: username,
            password: password
        },
        function(data,status){
            alert(status + data);
            $('#logonModel').modal('toggle');
        }
    );
}

$(function () {
  $('[data-toggle="tooltip"]').tooltip();
});

function initialiseDefaultView(){
	$("#viewMain").html("<div class='panel panel-default'><div class='panel-heading' id='securityNamePanelHeading'></div>"
	                    +"<div class='panel-body'>"
	                    +"<table id='quoteTable' class='table table-condensed'></table>"
	                    +"</div>"
	                    +"</div>"
	                    +"<ul class='nav nav-pills' role='tablist'>"
					    +"<li role='presentation' class='active'><a href='#realtime' aria-controls='realtime' role='tab' data-toggle='tab'>分时</a></li>"
						+"<li role='presentation'><a href='#dailyK' aria-controls='dailyK' role='tab' data-toggle='tab'>日K</a></li>"
						+"<li role='presentation'><a href='#weeklyK' aria-controls='weeklyK' role='weeklyK' data-toggle='tab'>周K</a></li>"
						+"<li role='presentation'><a href='#monthlyK' aria-controls='monthlyK' role='tab' data-toggle='tab'>月K</a></li>"
						+"</ul>"
						+"<div id='dailyContent' class='tab-content'>"
						+"<div role='tabpanel' class='tab-pane active' id='realtime'><div id='realtimeView' style='height:340px;'></div></div>"
						+"<div role='tabpanel' class='tab-pane' id='dailyK'></div>"
						+"<div role='tabpanel' class='tab-pane' id='weeklyK'>test22</div>"
						+"<div role='tabpanel' class='tab-pane' id='monthlyK'>test33</div>"
						+"</div>"
						+"<div id='dailyIndicatorContent'>"
						+"</div>");

	$("#viewRight").html("<ul class='nav nav-pills' role='tablist'>"
    					 +"<li role='presentation' class='active'><a href='#lodgedTransactionDetails' aria-controls='lodgedTransactionDetails' role='tab' data-toggle='tab'>五档盘口</a></li>"
    					 +"<li role='presentation'><a href='#portfolioDetails' aria-controls='portfolioDetails' role='tab' data-toggle='tab'>股本结构</a></li>"
    					 +"</ul>"
    					 +"<div class='tab-content'>"
    					 +"<div role='tabpanel' class='tab-pills-pane active' id='lodgedTransactionDetails'><table id='lodgedTransactionTable' class='table table-bordered table-condensed text-center'></table></div>"
    					 +"<div role='tabpanel' class='tab-pills-pane' id='portfolioDetails'><div id='portfolioDetailsView' style='height:200px;'></div></div>"
    					 +"</div>");

    $('a[href="#realtime"]').click(function(e){
        $("#dailyIndicatorNav").remove();
        $("#dailyIndicatorPane").remove();
    });

    $('a[href="#dailyK"]').click(function(e){
        e.preventDefault();
    	$("#dailyK").html("<div id='dailyView' style='width:" + $("#viewMain").width() +"px;" + "height:340px;'></div>");
    	dailyViewChart = echarts.init(document.getElementById('dailyView'));
    	fetchDailyQuote(selectedSecurityCode);
    	$("#dailyIndicatorContent").html("<div id='dailyIndicatorNav'>"
                      +"<ul class='nav nav-pills' role='tablist'>"
                      +"<li role='presentation' class='active'><a href='#rsi' aria-controls='rsi' role='tab' data-toggle='tab'>RSI</a></li>"
                      +"<li role='presentation'><a href='#mfi' aria-controls='mfi' role='tab' data-toggle='tab'>MFI</a></li>"
                      +"<li role='presentation'><a href='#macd' aria-controls='macd' role='tab' data-toggle='tab'>MACD</a></li>"
                      +"</ul>"
                      +"</div>"
                      +"<div id='dailyIndicatorPane' class='tab-content'>"
                      +"<div role='tabpanel' class='tab-pane active' id='rsi'><div id='rsiIndicatorView' style='height:200px;'></div></div>"
                      +"<div role='tabpanel' class='tab-pane' id='mfi'></div>"
                      +"<div role='tabpanel' class='tab-pane' id='macd'></div>"
                      +"</div>")
        rsiIndicatorChart = echarts.init(document.getElementById('rsiIndicatorView'));
        createRsiIndicator("daily", rsiIndicatorChart);
    	$(this).tab('show');

        $('a[href="#mfi"]').click(function(e){
            e.preventDefault();
            $("#mfi").html("<div id='fmiIndicatorView' style='width:" + $("#viewMain").width() +"px;" + "height:200px;'></div>");
            fmiIndicatorChart = echarts.init(document.getElementById('fmiIndicatorView'));
            createFMIIndicator("daily", fmiIndicatorChart);
            $(this).tab('show');
        });

    	$('a[href="#macd"]').click(function(e){
            e.preventDefault();
            $("#macd").html("<div id='macdIndicatorView' style='width:" + $("#viewMain").width() +"px;" + "height:200px;'></div>");
            macdIndicatorChart = echarts.init(document.getElementById('macdIndicatorView'));
            createMacdIndicator("daily", macdIndicatorChart);
            $(this).tab('show');
        });
    });

	// 基于准备好的dom，初始化echarts实例
	realtimeViewChart = echarts.init(document.getElementById('realtimeView'));
}

function createFMIIndicator(type,fmiIndicatorChart){
    var dates = [];
    var mfis =[];

    $.post(
        "http://192.168.3.14:9090/mytrade-application-1.0/indicator/mfi/" + type,
        {
            securityCode: selectedSecurityCode,
            fromDate: "2016-01-01"
        },
        function(data,status){
            for(var i=0; i< data.length; i++){
                dates.push(data[i].date);
                mfis.push(data[i].mfi);
            }

            var option = {
                legend: {
                    top: '3%',
                    data: [
                        {name: 'FMI', textStyle:{color: '#C23531', fontSize: 11}}
                    ],
                    inactiveColor: '#777',
                    left: 'left',
                    textStyle: {color: '#FBB117'}
                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        animation: false,
                        type: 'cross',
                            lineStyle: {color: '#B87333', width: 2, opacity: 1},
                            crossStyle: {color: '#B87333'}
                        },
                        textStyle:{
                            color: '#FBB117',
                            fontSize: 11
                        },
                        position: function (pos, params, el, elRect, size) {
                            var obj = {top: 10};
                            obj[['left', 'right'][+(pos[0] < size.viewSize[0] / 2)]] = 30;
                            return obj;
                        }
                    },
                grid: [
                    {top: '20%', left: '5%',right: '3%', height:'60%'}
                ],
                axisPointer: {
                    link: {xAxisIndex: 'all'},
                    label: {backgroundColor: '#222', borderColor: 'transparent', textStyle:{color: '#FBB117', fontSize: 11}}
                },
                xAxis: [
                    {
                        type: 'category',
                        data: dates,
                        scale: true,
                        boundaryGap: true,
                        axisLine: { lineStyle: { color: '#B87333', type: "dotted" } },
                        axisLabel: { show: true, textStyle:{fontSize:11}},
                        splitLine: { show: false },
                        axisTick: {show: false},
                        min: 'dataMin',
                        max: 'dataMax'
                    }
                ],
                yAxis: [
                    {
                        scale: true,
                        axisLine: { lineStyle: { color: '#B87333'} },
                        splitLine: { show: false },
                        axisLabel: { show: true, textStyle:{fontSize:11}},
                        splitLine: { show: true, lineStyle: { type:'dotted', opacity:'0.5', color: '#B87333'}},
                        min: '0',
                        max: '100'
                    }
                ],
                dataZoom: [
                    {
                        type: 'inside',
                        show: 'false',
                        start: 0,
                        end: 100
                    }
                ],
                animation: false,
                series: [
                    {
                        name: 'FMI',
                        type: 'line',
                        data: mfis,
                        smooth: true,
                        showSymbol: false,
                        lineStyle: {normal: {width: 1.5, color:'#C23531'}}
                    }
                ]
            }
            fmiIndicatorChart.setOption(option);
        }
    );
}

function createMacdIndicator(type,macdIndicatorChart){
    var dates = [];
    var macdSignal =[];
    var macdDiff=[];
    var macdHistogram = [];

    $.post(
        "http://192.168.3.14:9090/mytrade-application-1.0/indicator/macd/" + type,
        {
            securityCode: selectedSecurityCode,
            fromDate: "2016-01-01"
        },
        function(data,status){
            for(var i=0; i< data.length; i++){
                dates.push(data[i].date)
                macdDiff.push(data[i].difference);
                macdSignal.push(data[i].signal.ema);
                macdHistogram.push(data[i].histogram);
            }

            var option = {
                legend: {
                    top: '3%',
                    data: [
                        {name: 'DIFF', textStyle:{color: '#C23531', fontSize: 11}},
                        {name: 'DEA', textStyle:{color: '#61A0A8', fontSize: 11}},
                        {name: 'MACD', textStyle:{color: '#C23531', fontSize: 11}}
                    ],
                    inactiveColor: '#777',
                    left: 'left',
                    textStyle: {color: '#FBB117'}
                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        animation: false,
                        type: 'cross',
                            lineStyle: {color: '#B87333', width: 2, opacity: 1},
                            crossStyle: {color: '#B87333'}
                        },
                        textStyle:{
                            color: '#FBB117',
                            fontSize: 11
                        },
                        position: function (pos, params, el, elRect, size) {
                            var obj = {top: 10};
                            obj[['left', 'right'][+(pos[0] < size.viewSize[0] / 2)]] = 30;
                            return obj;
                        }
                    },
                grid: [
                    {top: '20%', left: '5%',right: '3%', height:'60%'}
                ],
                axisPointer: {
                    link: {xAxisIndex: 'all'},
                    label: {backgroundColor: '#222', borderColor: 'transparent', textStyle:{color: '#FBB117', fontSize: 11}}
                },
                xAxis: [
                    {
                        type: 'category',
                        data: dates,
                        scale: true,
                        boundaryGap: true,
                        axisLine: { lineStyle: { color: '#B87333', type: "dotted" } },
                        axisLabel: { show: true, textStyle:{fontSize:11}},
                        splitLine: { show: false },
                        axisTick: {show: false},
                        min: 'dataMin',
                        max: 'dataMax'
                    }
                ],
                yAxis: [
                    {
                        scale: true,
                        axisLine: { lineStyle: { color: '#B87333'} },
                        splitLine: { show: false },
                        axisLabel: { show: true, textStyle:{fontSize:11}},
                        splitLine: { show: true, lineStyle: { type:'dotted', opacity:'0.5', color: '#B87333'}},
                        min: Math.ceil(Math.max.apply(null,macdDiff)),
                        max: Math.floor(Math.min.apply(null,macdDiff))
                    }
                ],
                dataZoom: [
                    {
                        type: 'inside',
                        show: 'false',
                        start: 0,
                        end: 100
                    }
                ],
                animation: false,
                series: [
                    {
                        name: 'DIFF',
                        type: 'line',
                        data: macdDiff,
                        smooth: true,
                        showSymbol: false,
                        lineStyle: {normal: {width: 1.5, color:'#C23531'}}
                    },
                    {
                        name: 'DEA',
                        type: 'line',
                        data: macdSignal,
                        smooth: true,
                        showSymbol: false,
                        lineStyle: {normal: {width: 1.5, color:'#61A0A8'}}
                    },
                    {
                        name: 'MACD',
                        type: 'bar',
                        data: macdHistogram,
                        smooth: true,
                        showSymbol: false,
                        itemStyle: {
                            normal: {
                                color: function(params) {
                                    var colorList;
                                    if (params.data >= 0) {
                                        colorList = '#C23531';
                                    } else {
                                        colorList = '#61A0A8';
                                    }
                                    return colorList;
                                },
                            }
                        }
                    }
                ]
            }
            macdIndicatorChart.setOption(option);
        }
    );
}

function createRsiIndicator(type,rsiIndicatorChart){
    var rsiFast = [];
    var rsiMedium = [];
    var rsiSlow = [];
    var dates = [];

    $.post(
        "http://192.168.3.14:9090/mytrade-application-1.0/indicator/rsi/" + type,
        {
            securityCode: selectedSecurityCode,
            fromDate: "2016-01-01"
        },
        function(data,status){
            for(var i=0; i< data.length; i++){
                dates.push(data[i].date)
                rsiFast.push(data[i].fastRSI.rsi);
                rsiMedium.push(data[i].mediumRSI.rsi);
                rsiSlow.push(data[i].slowRSI.rsi);
            };

            var option = {
                legend: {
                    top: '3%',
                    data: [
                        {name: 'RSI6', textStyle:{color: '#c23531', fontSize: 11}},
                        {name: 'RSI12', textStyle:{color: '#2f4554', fontSize: 11}},
                        {name: 'RSI24', textStyle:{color: '#61a0a8', fontSize: 11}}
                    ],
                    inactiveColor: '#777',
                    left: 'left',
                    textStyle: {color: '#FBB117'}
                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                                animation: false,
                                type: 'cross',
                                lineStyle: {color: '#B87333', width: 2, opacity: 1},
                                crossStyle: {color: '#B87333'}
                            },
                            textStyle:{
                                color: '#FBB117',
                                fontSize: 11
                            },
                            position: function (pos, params, el, elRect, size) {
                                var obj = {top: 10};
                                obj[['left', 'right'][+(pos[0] < size.viewSize[0] / 2)]] = 30;
                                    return obj;
                                }
                        },
                        grid: [
                            {top: '20%', left: '5%',right: '3%', height:'60%'}
                        ],
                        axisPointer: {
                            link: {xAxisIndex: 'all'},
                        	label: {backgroundColor: '#222', borderColor: 'transparent', textStyle:{color: '#FBB117', fontSize: 11}}
                        },
                        xAxis: [
                            {
                                type: 'category',
                                data: dates,
                                scale: true,
                                boundaryGap: true,
                                axisLine: { lineStyle: { color: '#B87333' } },
                                axisLabel: { show: true, textStyle:{fontSize:11}},
                                splitLine: { show: false },
                                min: 'dataMin',
                                max: 'dataMax'
                            }
                        ],
                        yAxis: [
                            {
                                scale: true,
                                axisLine: { lineStyle: { color: '#B87333' } },
                                splitLine: { show: false },
                                axisLabel: { show: true, textStyle:{fontSize:11}},
                                splitLine: { show: true, lineStyle: { type:'dotted', opacity:'0.5', color: '#B87333'}},
                                min: '0',
                                max: '100'
                            }
                        ],
                        dataZoom: [
                            {
                                type: 'inside',
                                show: 'false',
                                start: 0,
                                end: 100
                            }
                        ],
                        animation: false,
                        series: [
                            {
                                name: 'RSI6',
                                type: 'line',
                                data: rsiFast,
                                smooth: true,
                                showSymbol: false,
                                lineStyle: {normal: {width: 1.5}}
                            },
                            {
                                name: 'RSI12',
                                type: 'line',
                                data: rsiMedium,
                                smooth: true,
                                showSymbol: false,
                                lineStyle: {normal: {width: 1.5}}
                            },
                            {
                                name: 'RSI24',
                                type: 'line',
                                data: rsiSlow,
                                smooth: true,
                                showSymbol: false,
                                lineStyle: {normal: {width: 1.5}}
                            }
                        ]
        }

            rsiIndicatorChart.setOption(option);
        }
    );
}

function fetchQuoteRealtime(){
    var securityName;
	var lastDayClosedAt;
	var times = [];
	var currentAt = [];
	var highestAllowedAt;
    var lowestAllowedAt;
    var currentPriceAt;
    var openedAt;
    var priceChange;
    var lastDayClosedAt;
    var highestAt;
    var lowestAt;
    var totalVolume;
    var totalValue;

    var exchangeVolumeRatio;
    var priceDifferenceRatio;
    var priceToBookRatio;
    var staticPriceEarningRatio;
    var forwardPriceEarningRatio;
    var lodgedBuySellRatio;
    var amplitudeRatio;

    var lodgedBuys;

    var buy1Volume;
    var buy1Price;
    var buy2Volume;
    var buy2Price;
    var buy3Volume;
    var buy3Price;
    var buy4Volume;
    var buy4Price;
    var buy5Volume;
    var buy5Price;

    var lodgedSells;

    var sell1Volume;
    var sell1Price;
    var sell2Volume;
    var sell2Price;
    var sell3Volume;
    var sell3Price;
    var sell4Volume;
    var sell4Price;
    var sell5Volume;
    var sell5Price;

    var totalCapitalVolume;
    var totalExchangedVolume;
    var netValuePerShare;
    var lastFourQuarterTotalEPS;
    var lastYearTotalEPS;
    var lastYearNetProfit;
    var lastFourQuarterNetProfit;

    $.post(
        "http://192.168.3.14:9090/mytrade-application-1.0/quote/list/realtime",
        {
            securityCode: selectedSecurityCode,
            timestamp: "09:00:00"
        },
        function(data,status){
            if(data.length >0){
                for (var i=0; i< data.length; i++){
                    times.push(data[i].quotePart.time);
					currentAt.push(data[i].quotePart.currentAt);
					if((i+1) == data.length){
                        securityName = data[i].securityProfile.securityName;
                        lastDayClosedAt = data[i].quotePart.lastDayClosedAt;
                        highestAllowedAt = data[i].quotePart.highestAllowedAt;
                        lowestAllowedAt = data[i].quotePart.lowestAllowedAt;

                        currentPriceAt = data[i].quotePart.currentAt;
                        openedAt=data[i].quotePart.openedAt;
                        priceChange=data[i].quotePart.priceChange;
                        highestAt=data[i].quotePart.highestAt;
                        lowestAt=data[i].quotePart.lowestAt;
                        totalVolume=data[i].quotePart.totalVolume/100;
                        totalValue=data[i].quotePart.totalValue/10000;

                        exchangeVolumeRatio=data[i].realtimeStatistics.exchangeVolumeRatio + "%";
                        priceDifferenceRatio=data[i].realtimeStatistics.priceDifferenceRatio +"%";
                        priceToBookRatio=data[i].realtimeStatistics.priceToBookRatio + "%";
                        staticPriceEarningRatio=data[i].realtimeStatistics.staticPriceEarningRatio + "%";
                        forwardPriceEarningRatio=data[i].realtimeStatistics.forwardPriceEarningRatio + "%";
                        lodgedBuySellRatio=data[i].realtimeStatistics.lodgedBuySellRatio + "%";
                        amplitudeRatio=data[i].realtimeStatistics.amplitudeRatio +"%";

                        totalCapitalVolume=data[i].securityPortfolio.totalCapitalVolume;
                        totalExchangedVolume=data[i].securityPortfolio.totalExchangedVolume;
                        netValuePerShare=data[i].securityPortfolio.netValuePerShare;
                        lastFourQuarterTotalEPS=data[i].securityPortfolio.lastFourQuarterTotalEPS;
                        lastYearTotalEPS=data[i].securityPortfolio.lastYearTotalEPS;
                        lastYearNetProfit=data[i].securityPortfolio.lastYearNetProfit;
                        lastFourQuarterNetProfit=data[i].securityPortfolio.lastFourQuarterNetProfit;


                        lodgedBuys=data[i].quotePart.lodgedBuys;
                        lodgedSells=data[i].quotePart.lodgedSell;

                        for (var j=0; j < lodgedBuys.length; j++){
                            if(j == 0){
                                buy1Volume=lodgedBuys[j].volume;
                                buy1Price=lodgedBuys[j].price;
                            }
                            if(j == 1){
                                buy2Volume=lodgedBuys[j].volume;
                                buy2Price=lodgedBuys[j].price;
                            }
                            if(j == 2){
                                buy3Volume=lodgedBuys[j].volume;
                                buy3Price=lodgedBuys[j].price;
                            }
                            if(j == 3){
                                buy4Volume=lodgedBuys[j].volume;
                                buy4Price=lodgedBuys[j].price;
                            }
                            if(j == 4){
                                buy5Volume=lodgedBuys[j].volume;
                                buy5Price=lodgedBuys[j].price;
                            }
                        }

                        for (var k=0; k < lodgedSells.length; k++){
                            if(k == 0){
                                sell1Volume=lodgedSells[k].volume;
                                sell1Price=lodgedSells[k].price;
                            }
                            if(k == 1){
                                sell2Volume=lodgedSells[k].volume;
                                sell2Price=lodgedSells[k].price;
                            }
                            if(k == 2){
                                sell3Volume=lodgedSells[k].volume;
                                sell3Price=lodgedSells[k].price;
                            }
                            if(k == 3){
                                sell4Volume=lodgedSells[k].volume;
                                sell4Price=lodgedSells[k].price;
                            }
                            if(k == 4){
                                sell5Volume=lodgedSells[k].volume;
                                sell5Price=lodgedSells[k].price;
                            }
                        }
					}
                };
            }

            $("#securityNamePanelHeading").text(securityName);

            $("#quoteTable").html("<tr><td width='5.25%' class='text-left'><h6>当前:</h6></td><td width='5.25%' class='text-left'><h6>"
                                  + currentPriceAt +"</h6></td><td width='5.25%' class='text-left'><h6>涨跌:</h6></td><td width='5.25%' class='text-left'><h6>"
                                  + priceChange + "</h6></td><td width='5.25%' class='text-left'><h6>今开:</h6></td><td width='5.25%' class='text-left'><h6>"
                                  + openedAt + "</h6></td><td width='5.25%' class='text-left'><h6>昨收:</h6></td><td width='5.25%' class='text-left'><h6>"
                                  + lastDayClosedAt + "</h6></td><td width='5.25%' class='text-left'><h6>最高:</h6></td><td width='5.25%' class='text-left'><h6>"
                                  + highestAt + "</h6></td><td width='5.25%' class='text-left'><h6>最低:</h6></td><td width='5.25%' class='text-left'><h6>"
                                  + lowestAt + "</h6></td><td width='6.5%' class='text-left'><h6>成交(手):</h6></td><td width='6.5%' class='text-left'><h6>"
                                  + totalVolume + "</h6></td><td width='6.5%' class='text-left'><h6>金额(万):</h6></td><td width='6.5%' class='text-left'><h6>"
                                  + totalValue + "</h6></td></tr><tr><td width='5.25%' class='text-left'><h6>涨幅:</h6></td><td width='5.25%' class='text-left'><h6>"
                                  + priceDifferenceRatio + "</h6></td><td width='5.25%' class='text-left'><h6>振幅:</h6></td><td width='5.25%' class='text-left'><h6>"
                                  + amplitudeRatio + "</h6></td><td width='5.25%' class='text-left'><h6>换手:</h6></td><td width='5.25%' class='text-left'><h6>"
                                  + exchangeVolumeRatio + "</h6></td><td width='5.25%' class='text-left'><h6>委比:</h6></td><td width='5.25%' class='text-left'><h6>"
                                  + lodgedBuySellRatio + "</h6></td><td width='5.25%' class='text-left'><h6>量比:</h6></td><td width='5.25%' class='text-left'><h6>"
                                  + exchangeVolumeRatio + "</h6></td><td width='5.25%' class='text-left'><h6>市净:</h6></td><td width='5.25%' class='text-left'><h6>"
                                  + priceToBookRatio + "</h6></td><td width='6.5%' class='text-left'><h6>市盈(静):</h6></td><td width='6.5%' class='text-left'><h6>"
                                  + staticPriceEarningRatio + "</h6></td><td width='6.5%' class='text-left'><h6>市盈(动):</h6></td><td width='6.5%' class='text-left'><h6>"
                                  + forwardPriceEarningRatio + "</h6></td></tr>");

            $("#lodgedTransactionTable").html("<tr><td width='25%'><h6>买一价:</h6></td><td width='25%'><h6>"
                                       + buy1Price +"</h6></td><td width='25%'><h6>买一量:</h6></td><td width='25%'><h6>"
                                       + buy1Volume + "</h6></td></tr><tr><td width='25%'><h6>买二价:</h6></td><td width='25%'><h6>"
                                       + buy2Price + "</h6></td><td width='25%'><h6>买二量:</h6></td><td width='25%'><h6>"
                                       + buy2Volume + "</h6></td></tr><tr><td width='25%'><h6>买三价:</h6></td><td width='25%'><h6>"
                                       + buy3Price + "</h6></td><td width='25%'><h6>买三量:</h6></td><td width='25%'><h6>"
                                       + buy3Volume + "</h6></td></tr><tr><td width='25%'><h6>买四价:</h6></td><td width='25%'><h6>"
                                       + buy4Price + "</h6></td><td width='25%'><h6>买四量:</h6></td><td width='25%'><h6>"
                                       + buy4Volume + "</h6></td></tr><tr><td width='25%'><h6>买五价:</h6></td><td width='25%'><h6>"
                                       + buy5Price + "</h6></td><td width='25%'><h6>买五量:</h6></td><td width='25%'><h6>"
                                       + buy5Volume + "</h6></td></tr><tr><td width='25%'><h6>卖一价:</h6></td><td width='25%'><h6>"
                                       + sell1Price +"</h6></td><td width='25%'><h6>卖一量:</h6></td><td width='25%'><h6>"
                                       + sell1Volume + "</h6></td></tr><tr><td width='25%'><h6>卖二价:</h6></td><td width='25%'><h6>"
                                       + sell2Price + "</h6></td><td width='25%'><h6>卖二量:</h6></td><td width='25%'><h6>"
                                       + sell2Volume + "</h6></td></tr><tr><td width='25%'><h6>卖三价:</h6></td><td width='25%'><h6>"
                                       + sell3Price + "</h6></td><td width='25%'><h6>卖三量:</h6></td><td width='25%'><h6>"
                                       + sell3Volume + "</h6></td></tr><tr><td width='25%'><h6>卖四价:</h6></td><td width='25%'><h6>"
                                       + sell4Price + "</h6></td><td width='25%'><h6>卖四量:</h6></td><td width='25%'><h6>"
                                       + sell4Volume + "</h6></td></tr><tr><td width='25%'><h6>卖五价:</h6></td><td width='25%'><h6>"
                                       + sell5Price + "</h6></td><td width='25%'><h6>卖五量:</h6></td><td width='25%'><h6>"
                                       + sell5Volume + "</h6></td></tr>");

            $("#porfolioTable").html("<tr><td width='25%'><h6>总股本</h6></td><td width='25%'><h6>"
                                    + totalCapitalVolume + "</h6></td><td width='25%'><h6>流通股本</h6></td><td width='25%'><h6>"
                                    + totalExchangedVolume + "</h6></td></tr><tr><td width='25%'><h6>收益和(年)</h6></td><td width='25%'><h6>"
                                    + lastYearTotalEPS + "</h6></td><td width='25%'><h6>收益和(4Q)</h6></td><td width='25%'><h6>"
                                    + lastFourQuarterTotalEPS + "</h6></td></tr><tr><td width='25%'><h6>盈利(年)</h6></td><td width='25%'><h6>"
                                    + lastYearNetProfit + "</h6></td><td width='25%'><h6>盈利(4Q)</h6></td><td width='25%'><h6>"
                                    + lastFourQuarterNetProfit + "</h6></tr><tr></td><td width='25%'><h6>净值(股)</h6></td><td width='25%'><h6>"
                                    + netValuePerShare + "</h6></td></tr>");

			// 指定图表的配置项和数据
			var option = {
				grid: [
                    {top: '8%', left: '5%',right: '2%', height: '80%'}
                ],
				tooltip: {
					trigger: 'axis',
					axisPointer: {
						animation: false,
						type: 'cross',
						lineStyle: {
							color: '#B87333',
							width: 2,
							opacity: 1
						},
						crossStyle: {
							color: '#B87333'
						}
					},
					textStyle:{
						color: '#FBB117',
						fontSize: 11
					},
					position: ["90%", "0%"]
				},
				axisPointer: {
					link: {xAxisIndex: 'all'},
					label: {
						backgroundColor: '#222',
						borderColor: 'transparent',
						textStyle:{
							color: '#FBB117',
							fontSize: 11
						}
					}
				},
				visualMap: {
                    top: 10,
                    right: 10,
                    precision: 2,
					show:false,
                    pieces: [{gt: lastDayClosedAt, lte:highestAllowedAt, color: '#FD1050'},{gt: lowestAllowedAt, lte: lastDayClosedAt, color: '#0CF49B'}],
                    outOfRange: {color: '#999'}
                },
				xAxis: {
				    type: 'category',
					data: times,
					axisLine: { lineStyle: { color: '#B87333' } },
					axisLabel: { show: true, textStyle:{fontSize:11}},
                    splitLine: { show: false }
				},
				yAxis: {
                    scale: true,
                    axisLine: { lineStyle: { color: '#B87333' } },
                    splitLine: { show: false },
                    axisLabel: { show: true, textStyle:{fontSize:11}},
                    min: Math.floor(lowestAllowedAt),
                    max: Math.ceil(highestAllowedAt),
                    sliptNumber: '6'
                },
				series: [{
					name: '当前',
					type: 'line',
					data: currentAt,
					showSymbol: false,
					lineStyle: {
						normal: {
							width: 2,
							opacity:0.6
						}
                    },
					markLine: {
                        silent: false,
                        label: {
                            normal: {
                                position: 'start',
								textStyle: {
									color: '#FBB117',
									fontSize: 11
								}
                            }
                        },
                        lineStyle: {
                            normal: {
                                color: '#B87333',
                                type: 'dotted'
                            }
                        },
                        data: [{yAxis:lastDayClosedAt}]
                    }
				}]
			};
			// 使用刚指定的配置项和数据显示图表。
			realtimeViewChart.setOption(option);
        }
    );
}

function calculateMA(dayCount, data) {
    var result = [];
    for (var i = 0, len = data.length; i < len; i++) {
        if (i < dayCount) {
            result.push('-');
            continue;
        }
        var sum = 0;
        for (var j = 0; j < dayCount; j++) {
            sum += data[i - j][1];
        }
        result.push((sum / dayCount).toFixed(2));
    }
    return result;
}

function fetchDailyQuote(){
    var dates = [];
    var quotes = [];
    var volumes = [];

    $.post(
        "http://192.168.3.14:9090/mytrade-application-1.0/quote/list",
        {
            securityCode: selectedSecurityCode,
            fromDate: "2016-01-01"
        },
        function(data,status){
            $.each(data, function(idx, obj) {
                dates.push(obj.quotePart.date);
                quotes.push([+obj.quotePart.openedAt, + obj.quotePart.adjustClosedAt, + obj.quotePart.lowestAt, +obj.quotePart.highestAt]);
                volumes.push(obj.quotePart.totalVolume);
            });
            var option = {
                legend: {
                    top: '2%',
                    data: [
                        {name: 'SMA5', textStyle:{color: '#c23531', fontSize: 11}},
                        {name: 'SMA10', textStyle:{color: '#2f4554', fontSize: 11}},
                        {name: 'SMA30', textStyle:{color: '#61a0a8', fontSize: 11}},
                        {name: 'SMA60', textStyle:{color: '#d48265', fontSize: 11}}
                    ],
                    inactiveColor: '#777',
                    left: 'left',
                    textStyle: {color: '#FBB117'}
                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        animation: false,
                        type: 'cross',
                        lineStyle: {color: '#B87333', width: 2, opacity: 1},
                        crossStyle: {color: '#B87333'}
                    },
                    textStyle:{
                        color: '#FBB117',
                        fontSize: 11
                    },
                    position: function (pos, params, el, elRect, size) {
                        var obj = {top: 10};
                        obj[['left', 'right'][+(pos[0] < size.viewSize[0] / 2)]] = 30;
                            return obj;
                        }
                },
                grid: [
                    {top: '12%', left: '5%',right: '3%', height:'60%'},
                    {top: '80%', left: '5%',right: '3%', height: '15%'}
                ],
                axisPointer: {
                    link: {xAxisIndex: 'all'},
                	label: {backgroundColor: '#222', borderColor: 'transparent', textStyle:{color: '#FBB117', fontSize: 11}}
                },
                xAxis: [
                    {
                        type: 'category',
                        data: dates,
                        scale: true,
                        boundaryGap: true,
                        axisLine: { lineStyle: { color: '#B87333' } },
                        axisLabel: { show: true, textStyle:{fontSize:11}},
                        splitLine: { show: false },
                        splitNumber: 20,
                        min: 'dataMin',
                        max: 'dataMax'
                    },
                    {
                        type: 'category',
                        gridIndex: 1,
                        data: dates,
                        scale: true,
                        boundaryGap: true,
                        axisLine: { lineStyle: { color: '#B87333' } },
                        splitLine: { show: false },
                        splitNumber: 20,
                        min: 'dataMin',
                        max: 'dataMax',
                        axisLabel: {show: false},
                        axisTick:{show: false}
                    }
                ],
                yAxis: [
                    {
                        scale: true,
                        axisLine: { lineStyle: { color: '#B87333' } },
                        splitLine: { show: false },
                        axisLabel: { show: true, textStyle:{fontSize:11}},
                    },
                    {
                        scale: true,
                        gridIndex: 1,
                        axisLine: { lineStyle: { color: '#B87333' } },
                        splitLine: { show: false },
                        axisLabel: {show: false},
                        axisTick:{show: true}
                    }
                ],
                dataZoom: [
                    {
                        type: 'inside',
                        show: 'false',
                        start: 0,
                        end: 100
                    }
                ],
                animation: false,
                series: [
                    {
                        type: 'candlestick',
                        name: '日K',
                        data: quotes,
                        itemStyle: {normal: {color: '#FD1050', color0: '#0CF49B', borderColor: '#FD1050', borderColor0: '#0CF49B'}}
                    },
                    {
                        name: 'SMA5',
                        type: 'line',
                        data: calculateMA(5, quotes),
                        smooth: true,
                        showSymbol: false,
                        lineStyle: {normal: {width: 1.5}}
                    },
                    {
                        name: 'SMA10',
                        type: 'line',
                        data: calculateMA(10, quotes),
                        smooth: true,
                        showSymbol: false,
                        lineStyle: {normal: {width: 1.5}}
                    },
                    {
                        name: 'SMA30',
                        type: 'line',
                        data: calculateMA(20, quotes),
                        smooth: true,
                        showSymbol: false,
                        lineStyle: {normal: {width: 1.5}}
                    },
                    {
                        name: 'SMA60',
                        type: 'line',
                        data: calculateMA(60, quotes),
                        smooth: true,
                        showSymbol: false,
                        lineStyle: {normal: {width: 1.5}}
                    },
                    {
                        name: '成交量',
                        type: 'bar',
                        xAxisIndex: 1,
                        yAxisIndex: 1,
                        data: volumes,
                        smooth: true,
                        showSymbol: false,
                        itemStyle: {normal: {color: '#B87333'}}
                    }
                ]
        };
        dailyViewChart.setOption(option);
    });
}