$ ->
  
  $.get "/home", (data)->
    
    #Carousel Articles
    cReviews=data.carouselArticles.carouselReviews
    cFeatures=data.carouselArticles.carouselFeatures
    cNews=data.carouselArticles.carouselNews
    cGloonicles=data.carouselArticles.carouselGloonicles
    
    
    #top users
    topUsers = data.top5Users
    
    
    #Recemt Articles
    recentAll=data.top10Articles.recentAllArticles
    recentReviews=data.top10Articles.recentReviews
    recentFeatures=data.top10Articles.recentFeatures
    recentNews=data.top10Articles.recentNews
    recentGloonicles=data.top10Articles.recentGloonicles
    
    
    #top games
    topGames = data.top10Games
      
    
    $.each cReviews, (index, cReview)->                                 
          $('#carouselReviews').append $("<li>").append $("<div>").text cReview.articleTitle+"("+cReview.articleAuthor+")"
        
    $.each cFeatures, (index, cFeature)->            
              $('#carouselFeatures').append $("<li>").text cFeature.articleTitle+"("+cFeature.articleAuthor+")"
              
    $.each cNews, (index, cNewsItem)->            
              $('#carouselNews').append $("<li>").text cNewsItem.articleTitle+"("+cNewsItem.articleAuthor+")"
              
    $.each cGloonicles, (index, cGloonicle)->            
              $('#carouselGloonicles').append $("<li>").text cGloonicle.articleTitle+"("+cGloonicle.articleAuthor+")"                                          
                        
      
    $.each topUsers, (index, topUser)->
      $('#top5users').append $("<li>").text topUser.userUserName
      
      
      
    $.each recentAll, (index, recentArticle)->                                 
          $('#recentAllarticles').append $("<li>").text recentArticle.articleTitle+"("+recentArticle.articleAuthor+")"
        
    $.each recentReviews, (index, recentReview)->            
              $('#recentReviews').append $("<li>").text recentReview.articleTitle+"("+recentReview.articleAuthor+")"
              
    $.each recentFeatures, (index, recentFeature)->            
              $('#recentFeatures').append $("<li>").text recentFeature.articleTitle+"("+recentFeature.articleAuthor+")"
              
    $.each recentNews, (index, recentNewsItem)->            
              $('#recentNews').append $("<li>").text recentNewsItem.articleTitle+"("+recentNewsItem.articleAuthor+")"
              
    $.each recentGloonicles, (index, recentGloonicle)->            
              $('#recentGloonicles').append $("<li>").text recentGloonicle.articleTitle+"("+recentGloonicle.articleAuthor+")"            
    
      
    $.each topGames, (index, topGame)->
      $('#top10games').append $("<li>").text topGame.gameTitle      
        
