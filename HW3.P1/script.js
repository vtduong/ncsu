var request = require('request');
var fs = require("fs");
var Promise = require('bluebird');
var parse = require('parse-link-header');

var token = "token " + "366b7e805c825e019c3ed7e078470d511590a8d9";

var unityId = "vtduong";

var newRepo = "newRepo";

var repo = "CSC326";

var collaborator = "bjcollin";

var visibility = "member";

var issueName = "Issue with creating new repo";

getYourRepos(unityId);
listBranches(unityId, repo);
createNewRepo(newRepo);
createIssue(unityId, repo, issueName);
enableWiki(unityId, repo);
addCollaborator(unityId, repo, collaborator);

//list all public repos of a given userName
function getYourRepos(userName)
{

	var options = {
		url: 'https://github.ncsu.edu/api/v3/users/' + userName + "/repos",
		method: 'GET',
		headers: {
			"User-Agent": "EnableIssues",
			"content-type": "application/json",
			"Authorization": token
		}
	};

	// Send a http request to url and specify a callback that will be called upon its return.
	request(options, function (error, response, body) 
	{
		var obj = JSON.parse(body);
		//console.log( obj );
		for( var i = 0; i < obj.length; i++ )
		{
			var name = obj[i].name;
			console.log( name );
		}
	});

}

//list all branches of a given repo with a given owner
function listBranches(owner,repo)
{
	var options = {
		url: 'https://github.ncsu.edu/api/v3/repos/' + owner + "/" + repo + "/branches",
		method: 'GET',
		headers: {
			"User-Agent": "EnableIssues",
			"content-type": "application/json",
			"Authorization": token
		}
	};

	// Send a http request to url and specify a callback that will be called upon its return.
	request(options, function (error, response, body) 
	{
		var obj = JSON.parse(body);

		for( var i = 0; i < obj.length; i++ )
		{
			var name = obj[i].name;
			console.log( name );
		}
	});
}

//create a new repo
function createNewRepo(repoName)
{
	var options = {
		url: 'https://github.ncsu.edu/api/v3/user/repos',
		method: 'POST',
		headers: {
			"User-Agent": "EnableIssues",
			"content-type": "application/json",
			"Authorization": token,
		},

		json: {
			"name": repoName,
			"description": "A repo created by POSTing",
		}

	};

	// Send a http request to url and specify a callback that will be called upon its return.
	request(options, function (error, response, body) 
	{
		console.log("repo created");
	});
}

//create an issue
function createIssue(owner, repo, issueName){
	var options = {
		url: 'https://github.ncsu.edu/api/v3/repos/' + owner +'/' + repo + '/issues',
		method: 'POST',
		headers: {
			"User-Agent": "EnableIssues",
			"content-type": "application/json",
			"Authorization": token,
		},

		json: {
			"title": issueName,
			"body": "I have a question regarding creating a new repo when trying to finish lab4. How do you tell if a new repo is created. Does GitHub not respond anything back after the POST command is executed?",

		}

	};

	// Send a http request to url and specify a callback that will be called upon its return.
	request(options, function (error, response, body) 
	{
		
		console.log("issue created");
	});
}

function enableWiki(owner,repo)
{
	var options = {
		url: 'https://github.ncsu.edu/api/v3/repos/' + owner + "/" + repo,
		method: 'PATCH',
		headers: {
			"User-Agent": "EnableIssues",
			"content-type": "application/json",
			"Authorization": token
		},

		json: {
			"name": repo,
			"has_wiki": true
		}
	};

	// Send a http request to url and specify a callback that will be called upon its return.
	request(options, function (error, response, body) 
	{
		console.log("wiki enabled");
	});
}

//add a collaborator to repo
function addCollaborator(owner,repo, username)
{
	var options = {
		url: 'https://github.ncsu.edu/api/v3/repos/'+owner+'/'+repo+'/collaborators/'+username+'?permission=admin',
		method: 'PUT',
		headers: {
			"User-Agent": "EnableIssues",
			"content-type": "application/json",
			"Authorization": token
		},

	};

	// Send a http request to url and specify a callback that will be called upon its return.
	request(options, function (error, response, body) 
	{
		console.log(username + " is added to "+ repo);
	});
}
