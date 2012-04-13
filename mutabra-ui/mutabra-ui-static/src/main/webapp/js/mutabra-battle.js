!function ($) {

  /* DESCRIPTION CLASS DEFINITION
   * ========================= */
  var Description = function (element, target) {
    this.$element = $(element);
    this.$target = target && $(target);
  };

  Description.prototype = {
    constructor:Description,

    show:function () {
      var $active = this.$target && this.$target.parent().children('.active');

      $active && $active.hide();
      this.$target && this.$target.show();
    },

    hide:function () {
      var $active = this.$target && this.$target.parent().children('.active');

      this.$target && this.$target.hide();
      $active && $active.show();
    },

    select: function() {
      var $active = this.$target && this.$target.parent().children('.active');

      this.$target && this.$target.removeClass('active');
      $active && $active.addClass('active');
    }
  };

  /* DESCRIPTION PLUGIN DEFINITION
   * ========================== */
  $.fn.description = function (option) {
    return this.each(function () {
      var $this = $(this),
          data = $this.data('description');
      if (!data) $this.data('description', (data = new Description(this, $this.data('description-target'))));
      if (typeof option == 'string') data[option]();
    });
  };

  $.fn.description.Constructor = Description;


  /* FIELD CLASS DEFINITION
   * ========================= */
  var Field = function (element, target) {
    this.$element = $(element);
    this.$target = target && $(target);
  };

  Field.prototype = {
    constructor:Field,

    select:function () {
      var $active = this.$target && this.$target.parent().children('.active'),
          $activeField = this.$element.parent().find('.active');

      this.$element.description('select');

      $active && $active.removeClass('active');
      this.$target && this.$target.addClass('active');

      $activeField && $activeField.attr('class', function(index, attr) {
        return attr.replace(' active', '');
      });
      this.$element.attr('class', function(index, attr) {
        return attr + ' active';
      });
    }
  };

  /* DESCRIPTION PLUGIN DEFINITION
   * ========================== */
  $.fn.field = function (option) {
    return this.each(function () {
      var $this = $(this),
          data = $this.data('field');
      if (!data) $this.data('field', (data = new Field(this, $this.data('field-target'))));
      if (typeof option == 'string') data[option]();
    });
  };

  $.fn.field.Constructor = Field;

  /* DESCRIPTION DATA-API
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
  });

}(window.jQuery);