!function ($) {

  /* DESCRIPTION CLASS DEFINITION
   * ========================= */
  var Description = function (element) {
    this.$element = $(element);

    var selector = this.$element.data('description-target');
    this.$target = selector && $(selector);
  };

  Description.prototype = {
    constructor:Description,

    show:function () {
      var $active = this.$target && this.$target.parent().find('.active');

      $active && $active.hide();
      this.$target && this.$target.show();
    },

    hide:function () {
      var $active = this.$target && this.$target.parent().find('.active');

      this.$target && this.$target.hide();
      $active && $active.show();
    },

    select: function() {
      var $active = this.$target && this.$target.parent().find('.active');

      $active && $active.removeClass('active');
      this.$target && this.$target.addClass('active');
    }
  };

  /* DESCRIPTION PLUGIN DEFINITION
   * ========================== */
  $.fn.description = function (option) {
    return this.each(function () {
      var $this = $(this),
          data = $this.data('description');
      if (!data) $this.data('description', (data = new Description(this)));
      if (typeof option == 'string') data[option]();
    });
  };

  $.fn.description.Constructor = Description;


  /* FIELD CLASS DEFINITION
   * ========================= */
  var Field = function (element) {
    this.$element = $(element);

    var selector = this.$element.data('field-target');
    this.$target = selector && $(selector);
  };

  Field.prototype = {
    constructor: Field,

    select:function () {
      var $active = this.$element.parent().find('.active');
      $active && $active.field('cancel');

      this.$element.attr('class', function(index, attr) {
        return attr + ' active';
      });
      this.$element.description('select');
      this.$target && this.$target.addClass('active');
    },

    cancel: function() {
      this.$element.attr('class', function(index, attr) {
        return attr.replace(' active', '');
      });
      this.$target && this.$target.removeClass('active');
    }
  };

  /* FIELD PLUGIN DEFINITION
   * ========================== */
  $.fn.field = function (option) {
    return this.each(function () {
      var $this = $(this),
          data = $this.data('field');
      if (!data) $this.data('field', (data = new Field(this)));
      if (typeof option == 'string') data[option]();
    });
  };

  $.fn.field.Constructor = Field;


  /* CARD CLASS DEFINITION
   * ========================= */
  var Card = function (element) {
    this.$element = $(element);

    var selector = this.$element.data('card-target');
    this.$target = selector && $(selector);
    this.url = this.$element.data('card-url');
  };

  Card.prototype = {
    constructor: Card,

    select: function () {
      var $active = this.$element.parents('.actions').find('.active');
      $active && $active.card('cancel');

      this.$element.addClass('active');
      this.$element.description('select');
      this.$target && this.$target.attr('class', function(index, attr) {
        return attr + ' highlight';
      });

      var card = this;
      this.$target && this.$target.on('click.card.data-api', function() {
        var $this = $(this),
            x = $this.data('position-x'),
            y = $this.data('position-y');
        card.apply(x, y);
      });
      $(document).on('keydown.card.data-api', function(event) {
        event.keyCode == 27 && card.cancel();
      });
    },

    cancel: function() {
      this.$element.removeClass('active');
      this.$target && this.$target.attr('class', function(index, attr) {
        return attr.replace(' highlight', '');
      });
      this.$target && this.$target.off('click.card.data-api');
      $(document).off('keydown.card.data-api');
    },

    apply: function(x, y) {
      $.ajax({
        url: this.url,
        data: {
          x: x,
          y: y
        }
      });
      this.cancel();
    }
  };

  /* CARD PLUGIN DEFINITION
   * ========================== */
  $.fn.card = function (option) {
    return this.each(function () {
      var $this = $(this),
          data = $this.data('card');
      if (!data) $this.data('card', (data = new Card(this)));
      if (typeof option == 'string') data[option]();
    });
  };

  $.fn.card.Constructor = Card;

  /* DATA-API
   * ============== */
  $(function () {
    $('body').on('mouseover.description.data-api', '[data-hover="description"]', function () {
      $(this).description('show');
    }).on('mouseout.description.data-api', '[data-hover="description"]', function () {
      $(this).description('hide');
    });

    $('body').on('click.field.data-api', '[data-select="field"]', function () {
      $(this).field('select');
    });

    $('body').on('click.card.data-api', '[data-select="card"]', function () {
      $(this).card('select');
    });
  });

}(window.jQuery);