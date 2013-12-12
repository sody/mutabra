var normalize = function(appearance) {
    appearance.sex = appearance.sex || 'MALE';
    if (!appearance.face || appearance.face.indexOf('f') >= 0) {
        appearance.face = Math.floor(Math.random() * 7);
    }
    appearance.eyes = appearance.eyes || Math.floor(Math.random() * 2);
    appearance.eyebrows = appearance.eyebrows || 0;
    appearance.ears = appearance.ears || 0;
    appearance.nose = appearance.nose || 0;
    appearance.mouth = appearance.mouth || Math.floor(Math.random() * 2);
    appearance.hair = appearance.hair || Math.floor(Math.random() * 4);
    appearance.facialHair = appearance.facialHair || 0;
    appearance.skinColor = appearance.skinColor || "#c3996b";
    appearance.eyeColor = appearance.eyeColor || "#38101c";
    appearance.hairColor = appearance.hairColor || "#222";
};

// normalize accounts
var accounts = db.accounts.find();
while (accounts.hasNext()) {
    var account = accounts.next();
    normalize(account && account.hero && account.hero.appearance);
    db.accounts.save(account);
}

// normalize heroes
var heroes = db.heroes.find();
while (heroes.hasNext()) {
    var hero = heroes.next();
    normalize(hero && hero.appearance);
    db.heroes.save(hero);
}

// normalize battles
var battles = db.battles.find();
while (battles.hasNext()) {
    var battle = battles.next();
    for (var i = 0; i < battle.heroes.length; i++) {
        normalize(battle.heroes[i] && battle.heroes[i].appearance);
    }
    db.battles.save(battle);
}
