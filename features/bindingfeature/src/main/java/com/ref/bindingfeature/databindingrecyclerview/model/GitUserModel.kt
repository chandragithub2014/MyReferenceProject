package com.ref.bindingfeature.databindingrecyclerview.model

class GitUserModel : ArrayList<GitUserModel.GitUserModelItem>(){
    data class GitUserModelItem(
        val avatar_url: String, // https://avatars2.githubusercontent.com/u/46?v=4
        val events_url: String, // https://api.github.com/users/bmizerany/events{/privacy}
        val followers_url: String, // https://api.github.com/users/bmizerany/followers
        val following_url: String, // https://api.github.com/users/bmizerany/following{/other_user}
        val gists_url: String, // https://api.github.com/users/bmizerany/gists{/gist_id}
        val gravatar_id: String,
        val html_url: String, // https://github.com/bmizerany
        val id: Int, // 46
        val login: String, // bmizerany
        val node_id: String, // MDQ6VXNlcjQ2
        val organizations_url: String, // https://api.github.com/users/bmizerany/orgs
        val received_events_url: String, // https://api.github.com/users/bmizerany/received_events
        val repos_url: String, // https://api.github.com/users/bmizerany/repos
        val site_admin: Boolean, // false
        val starred_url: String, // https://api.github.com/users/bmizerany/starred{/owner}{/repo}
        val subscriptions_url: String, // https://api.github.com/users/bmizerany/subscriptions
        val type: String, // User
        val url: String // https://api.github.com/users/bmizerany
    )
}