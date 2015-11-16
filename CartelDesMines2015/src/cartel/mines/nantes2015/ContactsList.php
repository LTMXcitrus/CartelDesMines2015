<?php
include("connexionbdd.php");
include("head.php");
?>

<div class="panel-body" >
		<label for="chooseUtilisateur" class="col-sm-4 control-label" >Utilisateurs: </label>
		<div class="col-sm-8">
			<select class="form-control" id="chooseUtilisateur" onchange="getUtilisateur(this)">
				<?$rep = $bd -> query('SELECT user FROM notifsToken');
				while($donnees = $rep ->fetch()){
				?>
					<option value="<?echo $donnees['user'];?>"><?echo $donnees['nom'];?></option>;
				<?}?>
			</select>
		</div>
		<div id="loading" style="display: none;">
			<p align="center"><img src="http://i.stack.imgur.com/FhHRx.gif" /></p>
		</div>
	</div>
	
	<div class="container col-md-4" id="results" align="center" style="display: inline-block; width:80%;">
		
	</div>
	
	<script type="text/javascript">
		function getUtilisateur(sel) {
			if(xhr != null){
				xhr.abort();
			}
			var value = sel.value;
			document.getElementById('loading').style.display = 'block';
			update(value);
		}
		var xhr = new XMLHttpRequest();
		function update(selection){
		xhr.open('GET', 'https://pist-minesnantes.rhcloud.com/?sport=' + selection );
		xhr.send(null);
		xhr.addEventListener('readystatechange', function() {
				if (xhr.readyState === 4 && xhr.status === 200) {
					document.getElementById('results').innerHTML = xhr.responseText;
					document.getElementById('loading').style.display = 'none';
				}
			}, false);
	}
	</script>
<?
include("footer.php");
?>

