// 选取 DOM 元素
const container = document.querySelector('#friend-big-box');
const leftDiv   = document.querySelector('.friend-index-box');
const rightDiv  = document.querySelector('.friend-info-box');
const resizer   = document.querySelector('.friend-resizer');

let isResizing = false;

/**
 * 初始化分隔条位置（可选）
 * 假设左侧是 50% 宽度，那么分隔条的 left 就是 50%。
 * 如果想动态计算当前 leftDiv 的实际宽度来同步，也可以在这里进行。
 */
function initResizerPosition() {
  resizer.style.left = '30%';
  leftDiv.style.width  = '30%';
  rightDiv.style.width = '70%';
}
initResizerPosition();

// 鼠标按下，开始拖拽
resizer.addEventListener('mousedown', (e) => {
  e.preventDefault();
  isResizing = true;
  // 在整个 document 监听移动/松开事件，方便拖出容器也能正常操作
  document.addEventListener('mousemove', handleMouseMove);
  document.addEventListener('mouseup', stopResizing);
});

function handleMouseMove(e) {
  if (!isResizing) return;
  
  // 计算出鼠标相对于 container 左边的距离
  const containerRect = container.getBoundingClientRect();
  let offsetX = e.clientX - containerRect.left; 
  
  // 做最小/最大范围限制（这里示例 10% ~ 90%）
  const minOffset = containerRect.width * 0.3;
  const maxOffset = containerRect.width * 0.5;
  if (offsetX < minOffset) offsetX = minOffset;
  if (offsetX > maxOffset) offsetX = maxOffset;

  // 转化成百分比
  const leftWidthPercent = (offsetX / containerRect.width) * 100;
  
  // 左、右盒子宽度都用百分比
  leftDiv.style.width  = leftWidthPercent + '%';
  rightDiv.style.width = (100 - leftWidthPercent) + '%';

  // 分隔条也用百分比定位
  resizer.style.left = leftWidthPercent + '%';
}

function stopResizing() {
  isResizing = false;
  document.removeEventListener('mousemove', handleMouseMove);
  document.removeEventListener('mouseup', stopResizing);
}
