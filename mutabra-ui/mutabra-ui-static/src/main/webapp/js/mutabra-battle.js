!function ($) {

  "use strict"

  /* DESCRIPTION CLASS DEFINITION
   * ========================= */
  var Description = function (element, options) {
    this.$element = $(element);
    this.options = options;
  };

  Description.prototype = {
    constructor:Description,

    show:function () {
      var selector = this.$element.data('target'),
          $target = selector && $(selector),
          $active = $target && $target.parent().children('.active');

      $active && $active.hide();
      $target && $target.show();
    },

    hide:function () {
      var selector = this.$element.data('target'),
          $target = selector && $(selector),
          $active = $target && $target.parent().children('.active');

      $target && $target.hide();
      $active && $active.show();
    },

    select:function () {
      var selector = this.$element.data('target'),
          $target = selector && $(selector),
          $active = $target && $target.parent().children('.active');

      $active && $active.removeClass('active');
      $target && $target.addClass('active');
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


  /* DESCRIPTION DATA-API
   * ============== */
  $(function () {
    $('body').on('mouseover.description.data-api', '[data-hover="description"]', function () {
      $(this).description('show');
    }).on('mouseout.description.data-api', '[data-hover="description"]', function () {
      $(this).description('hide');
    });

    $('body').on('click.description.data-api', '[data-select="description"]', function () {
      $(this).description('select');
    });
  });

}(window.jQuery);