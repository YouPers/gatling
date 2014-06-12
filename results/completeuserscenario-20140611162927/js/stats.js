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
		total: "30",
		ok: "-",
		ko: "30"
	},
	meanResponseTime : {
		total: "20",
		ok: "-",
		ko: "20"
	},
	standardDeviation : {
		total: "10",
		ok: "-",
		ko: "10"
	},
	percentiles1 : {
		total: "30",
		ok: "-",
		ko: "30"
	},
	percentiles2 : {
		total: "30",
		ok: "-",
		ko: "30"
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
		total: "4",
		ok: "2",
		ko: "2"
	},
	minResponseTime : {
		total: "10",
		ok: "10",
		ko: "10"
	},
	maxResponseTime : {
		total: "30",
		ok: "20",
		ko: "30"
	},
	meanResponseTime : {
		total: "18",
		ok: "15",
		ko: "20"
	},
	standardDeviation : {
		total: "7",
		ok: "5",
		ko: "10"
	},
	percentiles1 : {
		total: "30",
		ok: "20",
		ko: "30"
	},
	percentiles2 : {
		total: "30",
		ok: "20",
		ko: "30"
	},
	group1 : {
		name: "t < 800 ms",
		count: 2,
		percentage: 50
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
		percentage: 50
	},
	meanNumberOfRequestsPerSecond: {
		total: "0",
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