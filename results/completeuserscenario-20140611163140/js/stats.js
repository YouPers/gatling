var stats = {
	type: "GROUP",
contents: {
		
				"request-20-6804b307767c6d11be204fdc442a0815": {
		type: "REQUEST",
		name: "request_20",
path: "request_20",
pathFormatted: "request-20-6804b307767c6d11be204fdc442a0815",
stats: {
	numberOfRequests : {
		total: "2",
		ok: "2",
		ko: "0"
	},
	minResponseTime : {
		total: "10",
		ok: "10",
		ko: "-"
	},
	maxResponseTime : {
		total: "20",
		ok: "20",
		ko: "-"
	},
	meanResponseTime : {
		total: "15",
		ok: "15",
		ko: "-"
	},
	standardDeviation : {
		total: "5",
		ok: "5",
		ko: "-"
	},
	percentiles1 : {
		total: "20",
		ok: "20",
		ko: "-"
	},
	percentiles2 : {
		total: "20",
		ok: "20",
		ko: "-"
	},
	group1 : {
		name: "t < 800 ms",
		count: 2,
		percentage: 100
	},
	group2 : {
		name: "800 ms < t < 1200 ms",
		count: 0,
		percentage: 0
	},
	group3 : {
		name: "t > 1200 ms",
		count: 0,
		percentage: 0
	},
	group4 : {
		name: "failed",
		count: 0,
		percentage: 0
	},
	meanNumberOfRequestsPerSecond: {
		total: "0",
		ok: "0",
		ko: "-"
	}
}


	}
		,		
				"request-21-be4cb3b018858f862c1e735f75a6ecb1": {
		type: "REQUEST",
		name: "request_21",
path: "request_21",
pathFormatted: "request-21-be4cb3b018858f862c1e735f75a6ecb1",
stats: {
	numberOfRequests : {
		total: "2",
		ok: "0",
		ko: "2"
	},
	minResponseTime : {
		total: "10",
		ok: "-",
		ko: "10"
	},
	maxResponseTime : {
		total: "10",
		ok: "-",
		ko: "10"
	},
	meanResponseTime : {
		total: "10",
		ok: "-",
		ko: "10"
	},
	standardDeviation : {
		total: "0",
		ok: "-",
		ko: "0"
	},
	percentiles1 : {
		total: "10",
		ok: "-",
		ko: "10"
	},
	percentiles2 : {
		total: "10",
		ok: "-",
		ko: "10"
	},
	group1 : {
		name: "t < 800 ms",
		count: 0,
		percentage: 0
	},
	group2 : {
		name: "800 ms < t < 1200 ms",
		count: 0,
		percentage: 0
	},
	group3 : {
		name: "t > 1200 ms",
		count: 0,
		percentage: 0
	},
	group4 : {
		name: "failed",
		count: 2,
		percentage: 100
	},
	meanNumberOfRequestsPerSecond: {
		total: "0",
		ok: "-",
		ko: "0"
	}
}


	}
		,		
				"request-22-8ecb1f732969d171437218419f29c252": {
		type: "REQUEST",
		name: "request_22",
path: "request_22",
pathFormatted: "request-22-8ecb1f732969d171437218419f29c252",
stats: {
	numberOfRequests : {
		total: "2",
		ok: "0",
		ko: "2"
	},
	minResponseTime : {
		total: "10",
		ok: "-",
		ko: "10"
	},
	maxResponseTime : {
		total: "10",
		ok: "-",
		ko: "10"
	},
	meanResponseTime : {
		total: "10",
		ok: "-",
		ko: "10"
	},
	standardDeviation : {
		total: "0",
		ok: "-",
		ko: "0"
	},
	percentiles1 : {
		total: "10",
		ok: "-",
		ko: "10"
	},
	percentiles2 : {
		total: "10",
		ok: "-",
		ko: "10"
	},
	group1 : {
		name: "t < 800 ms",
		count: 0,
		percentage: 0
	},
	group2 : {
		name: "800 ms < t < 1200 ms",
		count: 0,
		percentage: 0
	},
	group3 : {
		name: "t > 1200 ms",
		count: 0,
		percentage: 0
	},
	group4 : {
		name: "failed",
		count: 2,
		percentage: 100
	},
	meanNumberOfRequestsPerSecond: {
		total: "0",
		ok: "-",
		ko: "0"
	}
}


	}
		},
name: "Global Information",
path: "",
pathFormatted: "missing-name",
stats: {
	numberOfRequests : {
		total: "6",
		ok: "2",
		ko: "4"
	},
	minResponseTime : {
		total: "10",
		ok: "10",
		ko: "10"
	},
	maxResponseTime : {
		total: "20",
		ok: "20",
		ko: "10"
	},
	meanResponseTime : {
		total: "12",
		ok: "15",
		ko: "10"
	},
	standardDeviation : {
		total: "2",
		ok: "5",
		ko: "0"
	},
	percentiles1 : {
		total: "20",
		ok: "20",
		ko: "10"
	},
	percentiles2 : {
		total: "20",
		ok: "20",
		ko: "10"
	},
	group1 : {
		name: "t < 800 ms",
		count: 2,
		percentage: 33
	},
	group2 : {
		name: "800 ms < t < 1200 ms",
		count: 0,
		percentage: 0
	},
	group3 : {
		name: "t > 1200 ms",
		count: 0,
		percentage: 0
	},
	group4 : {
		name: "failed",
		count: 4,
		percentage: 66
	},
	meanNumberOfRequestsPerSecond: {
		total: "1",
		ok: "0",
		ko: "0"
	}
}



}

function fillStats(stat){
    $("#numberOfRequests").append(stat.numberOfRequests.total);
    $("#numberOfRequestsOK").append(stat.numberOfRequests.ok);
    $("#numberOfRequestsKO").append(stat.numberOfRequests.ko);

    $("#minResponseTime").append(stat.minResponseTime.total);
    $("#minResponseTimeOK").append(stat.minResponseTime.ok);
    $("#minResponseTimeKO").append(stat.minResponseTime.ko);

    $("#maxResponseTime").append(stat.maxResponseTime.total);
    $("#maxResponseTimeOK").append(stat.maxResponseTime.ok);
    $("#maxResponseTimeKO").append(stat.maxResponseTime.ko);

    $("#meanResponseTime").append(stat.meanResponseTime.total);
    $("#meanResponseTimeOK").append(stat.meanResponseTime.ok);
    $("#meanResponseTimeKO").append(stat.meanResponseTime.ko);

    $("#standardDeviation").append(stat.standardDeviation.total);
    $("#standardDeviationOK").append(stat.standardDeviation.ok);
    $("#standardDeviationKO").append(stat.standardDeviation.ko);

    $("#percentiles1").append(stat.percentiles1.total);
    $("#percentiles1OK").append(stat.percentiles1.ok);
    $("#percentiles1KO").append(stat.percentiles1.ko);

    $("#percentiles2").append(stat.percentiles2.total);
    $("#percentiles2OK").append(stat.percentiles2.ok);
    $("#percentiles2KO").append(stat.percentiles2.ko);

    $("#meanNumberOfRequestsPerSecond").append(stat.meanNumberOfRequestsPerSecond.total);
    $("#meanNumberOfRequestsPerSecondOK").append(stat.meanNumberOfRequestsPerSecond.ok);
    $("#meanNumberOfRequestsPerSecondKO").append(stat.meanNumberOfRequestsPerSecond.ko);
}
