package onboarding

fun solution7(user: String, friends: List<List<String>>, visitors: List<String>): List<String> {

    var score: HashMap<String, Int> = hashMapOf() // key: 아이디, value: 추천 점수 의 형태로 HashMap 에 저장
    val userFriends: List<String> = findUserFriends(user, friends)  // user 의 친구 저장

    countFriendScore(user, userFriends, friends, score)
    countVisitScore(visitors, score, userFriends)

    val scoreIdList = sortScore(score).keys.toList()
    if (scoreIdList.size > 5){
        return scoreIdList.subList(0, 5)
    }
    return scoreIdList
}


//user 의 친구 찾기
fun findUserFriends(user: String, friends: List<List<String>>): List<String> {
    val userFriends = mutableListOf<String>()
    for (relation in friends) {
        if (relation.contains(user)) {
            if (relation.indexOf(user) == 0) {
                userFriends.add(relation[1])
            }
            if(relation.indexOf(user) == 1){
                userFriends.add(relation[0])
            }
        }
    }
    return userFriends
}

//함께아는 사람 점수 계산
fun countFriendScore(
    user: String,
    userFriends: List<String>,
    friends: List<List<String>>,
    score: HashMap<String, Int>
) {
    for (friend in userFriends) {
        for (relation in friends) {
            if (relation.contains(friend) && !relation.contains(user)) {
                if (relation.indexOf(friend) == 0) {
                    addScore(score, relation[1], 10)
                } else {
                    addScore(score, relation[0], 10)
                }
            }
        }
    }

}

//방문한 사람 점수 계산
fun countVisitScore(visitors: List<String>, result: HashMap<String, Int>, userFriends: List<String>) {
    for (visitor in visitors) {
        if (!userFriends.contains(visitor)) {
            addScore(result, visitor, 1)
        }
    }
}

fun addScore(score: HashMap<String, Int>, key: String, addScore:Int) {
    if (score.containsKey(key)) {
        score[key] = score[key]!! + addScore
    } else {
        score[key] = addScore
    }
}

//score 정렬하기
fun sortScore(score: HashMap<String, Int>): HashMap<String, Int> {
    val hashMapList = score.toList()
    val sortedList = hashMapList.sortedWith(
        compareBy(
            { -(it.second) }, { it.first }
        )
    )
    return sortedList.toMap() as HashMap
}