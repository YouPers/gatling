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
		ok: "0",
		ko: "2"
	},
	minResponseTime : {
		total: "10",
		ok: "-",
		ko: "10"
	},
	maxResponseTime : {
		total: "20",
		ok: "-",
		ko: "20"
	},
	meanResponseTime : {
		total: "15",
		ok: "-",
		ko: "15"
	},
	standardDeviation : {
		total: "5",
		ok: "-",
		ko: "5"
	},
	percentiles1 : {
		total: "20",
		ok: "-",
		ko: "20"
	},
	percentiles2 : {
		total: "20",
		ok: "-",
		ko: "20"
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
		total: "20",
		ok: "-",
		ko: "20"
	},
	meanResponseTime : {
		total: "15",
		ok: "-",
		ko: "15"
	},
	standardDeviation : {
		total: "5",
		ok: "-",
		ko: "5"
	},
	percentiles1 : {
		total: "20",
		ok: "-",
		ko: "20"
	},
	percentiles2 : {
		total: "20",
		ok: "-",
		ko: "20"
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
