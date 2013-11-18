var db = db || {
  faces: {
    insert: function(value) {}
  },
  levels: {
    insert: function(value) {}
  },
  cards: {
    insert: function(value) {}
  },
  races: {
    insert: function(value) {}
  }
};

// faces
db.faces.insert({_id: 'f1'});
db.faces.insert({_id: 'f2'});


// levels
db.levels.insert({
  _id: 'newbie',
  type: 'HERO',
  rating: 0
});


// plunger cards
db.cards.insert({
  _id: 'electric-ray',
  targetType: 'SINGLE_FRIEND_EMPTY',
  bloodCost: 2,
  effects: [
    {
      code: "electric-ray",
      type: 'SUMMON',
      targetType: 'SINGLE_FRIEND_EMPTY',
      duration: 1,
      power: 3,
      health: 5,
      abilities: [
        {
          code: 'electric-ray.attack',
          targetType: 'SINGLE_ENEMY_UNIT',
          bloodCost: 0,
          effects: [
            {
              code: 'electric-ray.attack',
              type: 'MAGIC_ATTACK',
              targetType: 'SINGLE_ENEMY_UNIT',
              duration: 1,
              power: 3,
              health: 0
            }
          ]
        }
      ]
    }
  ]
});

db.cards.insert({
  _id: 'trident-blow',
  targetType: 'SINGLE_ENEMY_UNIT',
  bloodCost: 2,
  effects: [
    {
      code: 'trident-blow',
      type: 'MELEE_ATTACK',
      targetType: 'SINGLE_ENEMY_UNIT',
      duration: 1,
      power: 6,
      health: 0
    }
  ]
});

db.cards.insert({
  _id: 'drop-of-the-ocean',
  targetType: 'ALL_FRIEND_UNIT',
  bloodCost: 2,
  effects: [
    {
      code: 'drop-of-the-ocean',
      type: 'HEAL',
      targetType: 'ALL_FRIEND_UNIT',
      duration: 1,
      power: 2,
      health: 0
    }
  ]
});

db.cards.insert({
  _id: 'swim-away',
  targetType: 'SINGLE_FRIEND',
  bloodCost: 2,
  effects: [
    {
      code: 'swim-away',
      type: 'MOVE',
      targetType: 'SINGLE_FRIEND',
      duration: 1,
      power: 2,
      health: 0
    }
  ]
});
//

db.cards.insert({
  _id: 'carrion-vulture',
  targetType: 'SINGLE_FRIEND_EMPTY',
  bloodCost: 2,
  effects: [
    {
      code: 'carrion-vulture',
      type: 'SUMMON',
      targetType: 'SINGLE_FRIEND_EMPTY',
      duration: 1,
      power: 2,
      health: 5,
      abilities: [
        {
          code: 'carrion-vulture.attack',
          targetType: 'SINGLE_ENEMY_UNIT',
          bloodCost: 0,
          effects: [
            {
              code: 'carrion-vulture.attack',
              type: 'RANGED_ATTACK',
              targetType: 'SINGLE_ENEMY_UNIT',
              duration: 1,
              power: 2,
              health: 0
            }
          ]
        }
      ]
    }
  ]
});

db.cards.insert({
  _id: 'snowball',
  targetType: 'ALL_ENEMY_UNIT',
  bloodCost: 1,
  effects: [
    {
      code: 'snowball',
      type: 'MAGIC_ATTACK',
      targetType: 'ALL_ENEMY_UNIT',
      duration: 1,
      power: 2,
      health: 0
    }
  ]
});

db.cards.insert({
  _id: 'throw',
  targetType: 'SINGLE_ENEMY_UNIT',
  bloodCost: 1,
  effects: [
    {
      code: 'throw',
      type: 'RANGED_ATTACK',
      targetType: 'SINGLE_ENEMY_UNIT',
      duration: 1,
      power: 3,
      health: 0
    }
  ]
});

// races

db.races.insert({
  _id: 'plunger',
  health: 30,
  mentalPower: 10,
  cards: [
    'electric-ray',
    'electric-ray',
    'electric-ray',
    'trident-blow',
    'trident-blow',
    'trident-blow',
    'drop-of-the-ocean',
    'drop-of-the-ocean',
    'drop-of-the-ocean',
    'swim-away',
    'swim-away',
    'swim-away'
  ]
});

db.races.insert({
  _id: 'flyer',
  health: 27,
  mentalPower: 12,
  cards: [
    'carrion-vulture',
    'carrion-vulture',
    'carrion-vulture',
    'carrion-vulture',
    'snowball',
    'snowball',
    'snowball',
    'snowball',
    'throw',
    'throw',
    'throw',
    'throw'
  ]
});
