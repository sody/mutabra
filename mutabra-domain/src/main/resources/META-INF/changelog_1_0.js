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
//

db.faces.insert({_id: 'f1'});
db.faces.insert({_id: 'f2'});

//

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
  _id: 'seahorse',
  targetType: 'SINGLE_FRIEND_EMPTY',
  bloodCost: 2,
  effects: [
    {
      code: "seahorse",
      type: 'SUMMON',
      targetType: 'SINGLE_FRIEND_EMPTY',
      duration: 1,
      power: 1,
      health: 3
    }
  ]
});

db.cards.insert({
  _id: 'mermaid',
  targetType: 'SINGLE_FRIEND_EMPTY',
  bloodCost: 2,
  effects: [
    {
      code: 'mermaid',
      type: 'SUMMON',
      targetType: 'SINGLE_FRIEND_EMPTY',
      duration: 1,
      power: 0,
      health: 4
    }
  ]
});

db.cards.insert({
  _id: 'calm',
  targetType: 'ALL_FRIEND_UNIT',
  bloodCost: 1,
  effects: [
    {
      code: 'calm',
      type: 'UNKNOWN',
      targetType: 'ALL_FRIEND_UNIT',
      duration: 3,
      power: 1,
      health: 0
    }
  ]
});

db.cards.insert({
  _id: 'wave',
  targetType: 'SINGLE_ENEMY_UNIT',
  bloodCost: 2,
  effects: [
    {
      code: 'wave',
      type: 'MAGIC_ATTACK',
      targetType: 'SINGLE_ENEMY_UNIT',
      duration: 1,
      power: 4,
      health: 0
    }
  ]
});

db.cards.insert({
  _id: 'whirlpool',
  targetType: 'SINGLE_ENEMY_EMPTY',
  bloodCost: 1,
  effects: [
    {
      code: 'whirlpool',
      type: 'UNKNOWN',
      targetType: 'SINGLE_ENEMY_EMPTY',
      duration: 1,
      power: 0,
      health: 0
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
  _id: 'swim-away',
  targetType: 'SINGLE_FRIEND_EMPTY',
  bloodCost: 0,
  effects: [
    {
      code: 'swim-away',
      type: 'UNKNOWN',
      targetType: 'SINGLE_FRIEND_EMPTY',
      duration: 1,
      power: 0,
      health: 0
    }
  ]
});

db.cards.insert({
  _id: 'storm',
  targetType: 'ALL_FRIEND_UNIT',
  bloodCost: 2,
  effects: [
    {
      code: 'storm',
      type: 'UNKNOWN',
      targetType: 'ALL_FRIEND_UNIT',
      duration: 1,
      power: 0,
      health: 0
    }
  ]
});

db.cards.insert({
  _id: 'drop-of-the-ocean',
  targetType: 'SINGLE_ENEMY_UNIT',
  bloodCost: 2,
  effects: [
    {
      code: 'drop-of-the-ocean',
      type: 'UNKNOWN',
      targetType: 'SINGLE_ENEMY_UNIT',
      duration: 2,
      power: 0,
      health: 0
    }
  ]
});

// flyer cards

db.cards.insert({
  _id: 'chamois',
  targetType: 'SINGLE_FRIEND_EMPTY',
  bloodCost: 2,
  effects: [
    {
      code: 'chamois',
      type: 'SUMMON',
      targetType: 'SINGLE_FRIEND_EMPTY',
      duration: 1,
      power: 2,
      health: 6,
      abilities: [
        {
          code: 'chamois.attack',
          targetType: 'SINGLE_ENEMY_UNIT',
          bloodCost: 0,
          effects: [
            {
              code: 'chamois.attack',
              type: 'MELEE_ATTACK',
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
  _id: 'carrion_vulture',
  targetType: 'SINGLE_FRIEND_EMPTY',
  bloodCost: 2,
  effects: [
    {
      code: 'carrion_vulture',
      type: 'SUMMON',
      targetType: 'SINGLE_FRIEND_EMPTY',
      duration: 1,
      power: 2,
      health: 5,
      abilities: [
        {
          code: 'carrion_vulture.attack',
          targetType: 'SINGLE_ENEMY_UNIT',
          bloodCost: 0,
          effects: [
            {
              code: 'carrion_vulture.attack',
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
  _id: 'chives',
  targetType: 'SINGLE_FRIEND_EMPTY',
  bloodCost: 2,
  effects: [
    {
      code: 'chives',
      type: 'SUMMON',
      targetType: 'SINGLE_FRIEND_EMPTY',
      duration: 1,
      power: 0,
      health: 1
    }
  ]
});

db.cards.insert({
  _id: 'scramble',
  targetType: 'SINGLE_FRIEND_HERO',
  bloodCost: 1,
  effects: [
    {
      code: 'scramble',
      type: 'UNKNOWN',
      targetType: 'SINGLE_FRIEND_HERO',
      duration: 1,
      power: 0,
      health: 0
    }
  ]
});

db.cards.insert({
  _id: 'scratch',
  targetType: 'SINGLE_ENEMY_HERO',
  bloodCost: 1,
  effects: [
    {
      code: 'scratch',
      type: 'MELEE_ATTACK',
      targetType: 'SINGLE_ENEMY_HERO',
      duration: 1,
      power: 5,
      health: 0
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

db.cards.insert({
  _id: 'falling-boulder',
  targetType: 'SINGLE_ENEMY_UNIT',
  bloodCost: 1,
  effects: [
    {
      code: 'falling-boulder',
      type: 'RANGED_ATTACK',
      targetType: 'SINGLE_ENEMY_UNIT',
      duration: 1,
      power: 7,
      health: 0
    }
  ]
});

db.cards.insert({
  _id: 'echo-mountain',
  targetType: 'ALL_UNIT',
  bloodCost: 2,
  effects: [
    {
      code: 'echo-mountain',
      type: 'UNKNOWN',
      targetType: 'ALL_UNIT',
      duration: 1,
      power: 4,
      health: 0
    }
  ]
});

db.cards.insert({
  _id: 'decompression',
  targetType: 'ALL_UNIT',
  bloodCost: 1,
  effects: [
    {
      code: 'decompression',
      type: 'UNKNOWN',
      targetType: 'ALL_UNIT',
      duration: 2,
      power: 1,
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
      'seahorse',
      'mermaid',
      'calm',
      'wave',
      'whirlpool',
      'trident-blow',
      'swim-away',
      'storm',
      'drop-of-the-ocean'
  ]
});

db.races.insert({
  _id: 'flyer',
  health: 27,
  mentalPower: 12,
  cards: [
    'chamois',
    'carrion-vulture',
    'chives',
    'scramble',
    'scratch',
    'snowball',
    'throw',
    'falling-boulder',
    'echo-mountain',
    'decompression'
  ]
});
